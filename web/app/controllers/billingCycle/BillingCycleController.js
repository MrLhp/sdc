angular.module("MetronicApp").controller('BillingCycleController',
    ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'BillingCycleService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, BillingCycleService) {
            $scope.$on('$viewContentLoaded', function () {
                // initialize core components
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            $scope.columns = BillingCycleService.getSchema();
            $scope.sort = BillingCycleService.getSort();
            $scope.order = BillingCycleService.getOrder();
            $scope.pageable = BillingCycleService.getPageable();
            $scope.selectable = true;

            $scope.list = function () {
                BillingCycleService.list().$promise.then(function (result) {
                    $scope.rows = result.data;

                    BillingCycleService.setStoredPage(result.pageable.number);
                    $scope.pageable = result.pageable;
                    if (!$scope.$$phase) {
                        $scope.$apply();
                    }
                });
            };

            let gotoFirstPage = function () {
                BillingCycleService.setStoredPage(0);
                $scope.list();
            };

            //start to fetch list data
            $scope.condition = {};
            BillingCycleService.clearSearchParams();
            gotoFirstPage();

            //fetch data when the size of page changed
            $scope.$watch('pageable.size', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                BillingCycleService.setSize(newVal);
                gotoFirstPage();
            });

            //fetch data when the number of pages changed
            $scope.$watch('pageable.number', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                BillingCycleService.setStoredPage(newVal);
                $scope.list();
            });

            $scope.$watch('sort', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                BillingCycleService.setSort(newValue);
                $scope.list();
            });

            $scope.$watch('order', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                BillingCycleService.setOrder(newValue);
                $scope.list();
            });

            $scope.view = function (id) {
                $scope.id = id;
                $location.path("/billingCycle/view.html").search({"id": id});
            };

            $scope.edit = function (id) {
                $location.path("/billingCycle/edit.html").search({"id": id});
            };

            $scope.create = function () {
                $location.path("/billingCycle/create.html");
            };

            $scope.del = function (id) {
                var modalInstance = $uibModal.open({
                    templateUrl: 'confirm.html',
                    controller: ["$scope", "$uibModalInstance", function (scope, $uibModalInstance) {
                        scope.confirmContent = "确认删除该账单吗？";
                        scope.btn_ok = function () {
                            $uibModalInstance.close(id);
                        };
                        scope.btn_cancel = function () {
                            $uibModalInstance.dismiss();
                        };
                    }]
                });

                modalInstance.result.then(function (id) {
                    BillingCycleService.delete(id).$promise.then(function (result) {
                        if ("success" == result.status) {
                            gotoFirstPage();
                            toastr.success("", "账单删除成功。");
                        } else {
                            for (var index in result.errors) {
                                toastr.error(result.errors[index].errmsg, "账单删除失败");
                            }
                        }
                    });
                });
            };
        }
    ]).controller('BillingCycleEditController',
    ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'BillingCycleService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, BillingCycleService) {
            $scope.$on('$viewContentLoaded', function () {
                // initialize core components
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            $scope.billingTypes = EnumService.get("billingType");
            //ng-table about
            $scope.columns = BillingCycleService.getDetailSchema();
            $scope.sort = BillingCycleService.getSort();
            $scope.order = BillingCycleService.getOrder();
            $scope.pageable = BillingCycleService.getPageable();
            $scope.selectable = true;

            $scope.id = $location.search().id;

            $scope.recordList=function () {
                BillingCycleService.getRecordList($scope.id).$promise.then(function (result) {
                    if ("success" == result.status) {
                        $scope.rows = result.data;
                    }
                });
            }

            $scope.updateBillCycle=function () {
                BillingCycleService.get($scope.id).$promise.then(function (result) {
                    if ("success" == result.status) {
                        $scope.model = result.data;
                        $scope.model.billingMoney = $scope.model.billingMoney/100;
                        $scope.model.amountMoney = $scope.model.amountMoney/100;
                        $scope.copy_model = angular.copy($scope.model);
                    } else {
                        for (var index in result.errors) {
                            toastr.error("账单信息获取失败，", result.errors[index].errmsg);
                        }
                    }
                });
            }

            if ($scope.id) {
                //编辑
                $scope.updateBillCycle();
                $scope.recordList();
            }else {
                //新建
                $scope.model = {};
                $scope.model.disabled = false;
                $scope.copy_model = angular.copy($scope.model);
            }

            $scope.submit = function (form, formCorporation) {
                form.$submitted = true;
                formCorporation.$submitted = true;
                if (form.$valid && formCorporation.$valid) {
                    BillingCycleService.save($scope.model).$promise.then(function (result) {
                        if ("success" == result.status) {
                            toastr.success("", "账单信息保存成功");
                        } else {
                            for (var index in result.errors) {
                                toastr.error(result.errors[index].errmsg, "账单信息保存失败");
                            }
                        }
                    });
                }
            };

            $scope.reset = function () {
                $scope.model = angular.copy($scope.copy_model);
            };

            $scope.back = function () {
                $location.path("/billingCycle/list.html").search({});
            };

            $scope.record = {};
            $scope.showRecordCreate = function () {
                let modalInstance = $uibModal.open({
                    templateUrl: 'showBillingRecordModal.html',
                    controller: ['$scope', '$uibModalInstance', function ($scope, $uibModalInstance) {
                        $scope.recordTypes = EnumService.get("recordType");
                        $scope.btn_ok = function () {
                            $uibModalInstance.close($scope.record);
                        };

                        $scope.btn_cancel = function () {
                            $uibModalInstance.dismiss();
                        };
                    }]
                });

                modalInstance.result.then(function (record) {
                    record.billingCycle={id:$scope.id}
                    record.paymentBillDate = angular.element("#paymentBillDate").val();
                    BillingCycleService.saveBillingRecord(record).$promise.then(function (result) {
                        if ("success" == result.status) {
                            toastr.success("", "账单记录添加成功");
                            $scope.updateBillCycle();
                            $scope.recordList();
                        } else {
                            for (var index in result.errors) {
                                toastr.error(result.errors[index].errmsg, "账单记录添加失败");
                            }
                        }
                    })
                })
            }
        }
]).filter("billingTypesFilter",["EnumService",function (EnumService) {
    let billingType = EnumService.get("billingType");
    return function (value) {
        let even = _.find(billingType, {"key": value});
        return even?even.text:value;
    }
}]).filter("isFinishFilter",["$filter",function ($filter) {
    return function (value) {
        return value?"已结清":"未结清";
    }
}]);