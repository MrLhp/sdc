angular.module("MetronicApp").controller('EqualAmountPreviewController',
    ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'EqualAmountPreviewService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, EqualAmountPreviewService) {
            $scope.$on('$viewContentLoaded', function () {
                // initialize core components
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            $scope.equalAmountTypes = EnumService.get("equalAmountTypes");
            //ng-table about
            $scope.columns = EqualAmountPreviewService.getSchema();
            $scope.sort = EqualAmountPreviewService.getSort();
            $scope.order = EqualAmountPreviewService.getOrder();
            $scope.pageable = EqualAmountPreviewService.getPageable();
            $scope.selectable = true;

            $scope.condition = {
                quota: 10000,
                interestRate: 0.0004,
                interestRebate: 0,
                dateOfLoan: moment(),
                stageNumberOfMonth: 12,
                equalAmountType: "interest"
            };

            EqualAmountPreviewService.clearSearchParams();

            $scope.showCreate = function () {
                let modalInstance = $uibModal.open({
                    templateUrl: 'showEqualAmountModal.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.equalAmountSources = EnumService.get("equalAmountSources");

                        $scope.btn_ok = function () {
                            $uibModalInstance.close($scope.condition);
                        };

                        $scope.btn_cancel = function () {
                            $uibModalInstance.dismiss();
                        };
                    }]
                });

                modalInstance.result.then(function (condition) {
                    $scope.condition.equalAmountName = condition.equalAmountName;
                    $scope.condition.equalAmountSource = condition.equalAmountSource;
                    EqualAmountPreviewService.saveEqualAmount($scope.condition).$promise.then(function (result) {
                        if (result.data == "success") {
                            toastr.success("创建成功!");
                        }
                    });
                })
            }

            //search
            $scope.search = function () {
                //总还款
                $scope.sumPayMoney = 0;
                //总利息
                $scope.sumInterest = 0;
                
                EqualAmountPreviewService.postForm($scope.condition).$promise.then(function (result) {
                    $scope.rows = result.data;
                    if (!$scope.$$phase) {
                        $scope.$apply();
                    }
                    angular.forEach(result.data, function (k, v) {
                        $scope.sumPayMoney += k.repaymentMoney;
                        $scope.sumInterest += k.interest;
                    })
                    $scope.sumPayMoney / 100;
                    $scope.sumInterest / 100;
                });


            };
        }
    ]
).controller('EqualAmountListController', ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'EqualAmountListService','EqualAmountPreviewService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, EqualAmountListService,EqualAmountPreviewService) {
            $scope.$on('$viewContentLoaded', function () {
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;

                $scope.columns = EqualAmountListService.getSchema();
                $scope.sort = EqualAmountListService.getSort();
                $scope.order = EqualAmountListService.getOrder();
                $scope.pageable = EqualAmountListService.getPageable();
                $scope.selectable = true;

                $scope.equalAmountTypes = EnumService.get("equalAmountTypes");
                $scope.equalAmountSources = EnumService.get("equalAmountSources");

                $scope.list = function () {
                    EqualAmountListService.list().$promise.then(function (result) {
                        $scope.rows = result.data;

                        EqualAmountListService.setStoredPage(result.pageable.number);
                        $scope.pageable = result.pageable;
                        if (!$scope.$$phase) {
                            $scope.$apply();
                        }
                    });
                };

                var gotoFirstPage = function () {
                    EqualAmountListService.setStoredPage(0);
                    $scope.list();
                };

                //start to fetch list data
                $scope.condition = {};
                EqualAmountListService.clearSearchParams();
                gotoFirstPage();

                //fetch data when the size of page changed
                $scope.$watch('pageable.size', function (newVal, oldVal) {
                    if (newVal == oldVal) return;
                    EqualAmountListService.setSize(newVal);
                    gotoFirstPage();
                });

                //fetch data when the number of pages changed
                $scope.$watch('pageable.number', function (newVal, oldVal) {
                    if (newVal == oldVal) return;
                    EqualAmountListService.setStoredPage(newVal);
                    $scope.list();
                });

                $scope.$watch('sort', function (newValue, oldValue) {
                    if (oldValue == newValue) return;
                    EqualAmountListService.setSort(newValue);
                    $scope.list();
                });

                $scope.$watch('order', function (newValue, oldValue) {
                    if (oldValue == newValue) return;
                    EqualAmountListService.setOrder(newValue);
                    $scope.list();
                });

                $scope.create = function () {
                    $location.path("/student/create.html").search({});
                };

                //search
                $scope.search = function () {
                    EqualAmountListService.putSearchParams(
                        {
                            name: $scope.condition.name
                        }
                    );
                    gotoFirstPage();
                };

                //跳转页面并传递参数
                $scope.view = function (id) {
                    $scope.viewid = id;
                    $location.path("/student/view.html").search({"id": id});
                };

                $scope.edit = function (id) {
                    $location.path("/student/edit.html").search({"id": id});
                };

                $scope.del = function (id) {
                    //todo:实现删除
                };

                $scope.toPreview = function () {
                    $location.path("/equalAmount/preview.html")
                };

                $scope.embed = {
                    columns: EqualAmountPreviewService.getSchema(),
                    sort: EqualAmountPreviewService.getSort(),
                    order: EqualAmountPreviewService.getOrder(),
                    pageable: EqualAmountPreviewService.getPageable(),
                    rows: []
                };

                $scope.list();
                $scope.listEmbed = function (row) {
                    EqualAmountPreviewService.list(function (res) {
                        if ('success' == res.status) {
                            $scope.embed.rows = res.data;
                        } else {
                            toastr.error("", "查询异常！");
                        }
                    })
                };
            });

            $scope.onRowClicked = function (row) {
                EqualAmountPreviewService.setStoredPage(0);
                $scope.listEmbed(row);
            };


            $scope.$watch('embed.pageable.size', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountPreviewService.setSize(newVal);
                $scope.listEmbed($scope.model.id);
            });


            $scope.$watch('embed.pageable.number', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountPreviewService.setStoredPage(newVal);
                $scope.listEmbed($scope.model.id);
            });
        }
    ]
).filter("equalAmountTypesFilter",["EnumService",function (EnumService) {
    let equalAmountTypes = EnumService.get("equalAmountTypes");
    return function (value) {
        let even = _.find(equalAmountTypes, {"key": value});
        return even?even.text:value;
    }
}]).filter("equalAmountSourcesFilter",["EnumService",function (EnumService) {
    let equalAmountSources = EnumService.get("equalAmountSources");
    return function (value) {
        let even = _.find(equalAmountSources, {"key": value});
        return even?even.text:value;
    }
}]);