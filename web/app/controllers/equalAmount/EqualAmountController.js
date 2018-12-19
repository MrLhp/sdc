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
).controller('EqualAmountEditController',
    ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'EqualAmountPreviewService', 'EqualAmountListService','EqualAmountDetailService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, EqualAmountPreviewService, EqualAmountListService,EqualAmountDetailService) {
            $scope.$on('$viewContentLoaded', function () {
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            $scope.columns = EqualAmountDetailService.getSchema();
            $scope.sort = EqualAmountDetailService.getSort();
            $scope.order = EqualAmountDetailService.getOrder();
            $scope.pageable = EqualAmountDetailService.getPageable();
            $scope.selectable = true;
            $scope.noheadpageable=true;
            $scope.pageable.size=25;

            $scope.equalAmountTypes = EnumService.get("equalAmountTypes");
            $scope.equalAmountSources = EnumService.get("equalAmountSources");


            $scope.list = function () {
                EqualAmountDetailService.list({id:$location.search().id}).$promise.then(function (result) {
                    if ("success" == result.status) {
                        $scope.rows = result.data;
                        $scope.pageable = result.pageable;

                        for (var i = 0; i < $scope.rows.length; i++) {
                            if ($scope.rows[i].payment) {
                                $scope.rows[i].realPayment=true;
                                $scope.rows[i].selected = true
                            }
                        }

                        if (!$scope.$$phase) {
                            $scope.$apply();
                        }
                    }
                });
            };

            var gotoFirstPage = function () {
                EqualAmountDetailService.setStoredPage(0);
                $scope.list();
            };

            EqualAmountDetailService.clearSearchParams();
            gotoFirstPage();

            var statisticsInfo = function () {
                EqualAmountListService.statisticsInfo($location.search().id).$promise.then(function (result) {
                    if ("success" == result.status) {
                        $scope.model=result.data;
                        $scope.copy_model=angular.copy($scope.model);
                        $scope.model.amountTotalMoney=$scope.model.amountTotalMoney/100;
                    }
                });
            };

            statisticsInfo();

            $scope.checkedList = [];
            $scope.submit = function (form, formCorporation) {
                form.$submitted = true;
                formCorporation.$submitted = true;
                if (form.$valid && formCorporation.$valid) {
                    let paymentDto={
                        id:$location.search().id,
                        statisticId: $location.search().id,
                        equalAmountName:$scope.model.equalAmountName,
                        resultDTOS:[]
                    };
                    $scope.rows.forEach(function (v,i) {
                        if (v.realPayment == undefined) {
                            paymentDto.resultDTOS.push({resultId:v.id,resultNo:v.no,realRepaymentDate:v.repaymentDate,realPayment:false});
                        }else{
                            paymentDto.resultDTOS.push({resultId:v.id,resultNo:v.no,realRepaymentDate:v.repaymentDate,realPayment:v.realPayment});
                        }
                    });

                    EqualAmountDetailService.updateEqualAmount($location.search().id, paymentDto).$promise.then(function (result) {
                        if ('success' == result.status) {
                            toastr.success("更新成功", "系统消息");
                            statisticsInfo();
                            $scope.list();
                        }else {
                            toastr.error("", "保存异常！");
                        }

                    });
                }
            };


            $scope.reset = function () {
                $scope.model = angular.copy($scope.copy_model);
            };

            $scope.back = function () {
                $location.path("/equalAmount/list.html").search({});
            };

            $scope.dateClick = function () {
                $scope.row.chk=true;
                $scope.row.realPayment=true;
            };

            $scope.onRowClicked = function (row, $event) {
                if (!row.chk) {
                    row.realPayment=false;
                }else{
                    row.realPayment=true;
                }
                $scope.row=row;
            };

            $scope.$watch('pageable.size', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountDetailService.setSize(newVal);
                gotoFirstPage();
            });

            $scope.$watch('pageable.number', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountDetailService.setStoredPage(newVal);
                $scope.list();
            });

            $scope.$watch('sort', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                EqualAmountDetailService.setSort(newValue);
                $scope.list();
            });

            $scope.$watch('order', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                EqualAmountDetailService.setOrder(newValue);
                $scope.list();
            });
        }
    ]
).controller('EqualAmountListController', ['$rootScope', '$scope', '$location', '$uibModal', 'EnumService', "toastr", 'EqualAmountListService','EqualAmountDetailService',
        function ($rootScope, $scope, $location, $uibModal, EnumService, toastr, EqualAmountListService,EqualAmountDetailService) {
                $scope.$on('$viewContentLoaded', function () {
                    App.initAjax();
                    $rootScope.settings.layout.pageBodySolid = true;
                    $rootScope.settings.layout.pageSidebarClosed = false;
                });


                $scope.columns = EqualAmountListService.getSchema();
                $scope.sort = EqualAmountListService.getSort();
                $scope.order = EqualAmountListService.getOrder();
                $scope.pageable = EqualAmountListService.getPageable();
                $scope.selectable = true;

                $scope.equalAmountTypes = EnumService.get("equalAmountTypes");
                $scope.equalAmountSources = EnumService.get("equalAmountSources");

                $scope.sumPayMoney = 0;

                $scope.list = function () {
                    EqualAmountListService.list().$promise.then(function (result) {
                        $scope.rows = result.data;

                        angular.forEach(result.data, function (k, v) {
                            $scope.sumPayMoney += k.amountTotalMoney;
                        })

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
                    $location.path("/equalAmount/edit.html").search({"id": id});
                };

                $scope.del = function (id) {
                    var modalInstance = $uibModal.open({
                        templateUrl: 'confirm.html',
                        controller: ["$scope", "$uibModalInstance", function (scope, $uibModalInstance) {
                            scope.confirmContent = "确认删除该贷款吗？";
                            scope.btn_ok = function () {
                                $uibModalInstance.close(id);
                            };
                            scope.btn_cancel = function () {
                                $uibModalInstance.dismiss();
                            };
                        }]
                    });

                    modalInstance.result.then(function (id) {
                        EqualAmountListService.delete(id).$promise.then(function (result) {
                            if ("success" == result.status) {
                                gotoFirstPage();
                                toastr.success("", "删除成功。");
                            } else {
                                for (var index in result.errors) {
                                    toastr.error(result.errors[index].errmsg, "删除失败");
                                }
                            }
                        });
                    });
                };

                $scope.toPreview = function () {
                    $location.path("/equalAmount/preview.html")
                };

                $scope.embed = {
                    columns: EqualAmountDetailService.getSchema(),
                    sort: EqualAmountDetailService.getSort(),
                    order: EqualAmountDetailService.getOrder(),
                    pageable: EqualAmountDetailService.getPageable(),
                    rows: []
                };

                $scope.detailid;
                $scope.parentRows;
                $scope.listEmbed = function (row) {
                    if (row !== undefined) {
                        $scope.detailid=row.id;
                        $scope.parentRows=row;
                    }
                    EqualAmountDetailService.list({id:$scope.detailid},function (res) {
                        if ('success' == res.status) {
                            $scope.embed.rows = res.data;
                            $scope.embed.pageable = res.pageable;

                        } else {
                            if (result.errors.length >= 0) {
                                angular.forEach(result.errors, function (each) {
                                    toastr.error("", each.errmsg);
                                });
                            } else {
                                toastr.error("", "查询异常！");
                            }
                        }
                    })
                };

            $scope.onRowClicked = function (row) {
                // EqualAmountDetailService.setStoredPage(0);
                // $scope.listEmbed(row);
            };


            $scope.$watch('embed.pageable.size', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountDetailService.setSize(newVal);
                $scope.listEmbed($location.detailid);
            });


            $scope.$watch('embed.pageable.number', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                EqualAmountDetailService.setStoredPage(newVal);
                $scope.listEmbed($location.detailid);
            });

            $scope.saveRealDate = function (row) {
                let paymentDto={
                    id:$scope.parentRows.id,
                    statisticId: $scope.parentRows.id,
                    resultDTOS:[]
                };
                paymentDto.resultDTOS.push({resultId:row.id,resultNo:row.no,realRepaymentDate:row.repaymentDate});
                EqualAmountDetailService.updateEqualAmount(row.id, paymentDto).$promise.then(function (result) {
                    if ('success' == result.status) {
                        $scope.listEmbed($location.detailid);
                    }else {
                        toastr.error("", "保存异常！");
                    }

                });
            }
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
}]).filter("isPaymentFilter",["$filter",function ($filter) {
    return function (value) {
        return value?"已还款":"未还款";
    }
}]).filter("timestampToDateExt",["$filter",function ($filter) {
    return function (value) {
        if (value._i != undefined) {
            value=value._i;
        }
        const filter = $filter("date");
        return filter(value, "yyyy-MM-dd");
    }
}]);