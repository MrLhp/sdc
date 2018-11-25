angular.module("MetronicApp").controller('EqualAmountPreviewController',
    ['$rootScope', '$scope', '$location', '$uibModal','EnumService', "toastr", 'EqualAmountPreviewService',
        function ($rootScope, $scope, $location, $uibModal,EnumService, toastr, EqualAmountPreviewService) {
            $scope.$on('$viewContentLoaded', function () {
                // initialize core components
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            $scope.equalAmountTypes = EnumService.get("equalAmountType");
            //ng-table about
            $scope.columns = EqualAmountPreviewService.getSchema();
            $scope.sort = EqualAmountPreviewService.getSort();
            $scope.order = EqualAmountPreviewService.getOrder();
            $scope.pageable = EqualAmountPreviewService.getPageable();
            $scope.selectable = true;

            //总还款
            $scope.sumPayMoney = 0;
            //利息
            $scope.sumInterest = 0;

            $scope.condition = {
                quota: 10000,
                interestRate: 0.0004,
                interestRebate: 0,
                dateOfLoan: moment(),
                stageNumberOfMonth: 12,
                equalAmountType: "interest"
            };

            EqualAmountPreviewService.clearSearchParams();

            $scope.showCreate=function(){
                let modalInstance = $uibModal.open({
                    templateUrl: 'showEqualAmountModal.html',
                    controller: ['$scope','$uibModalInstance',function ($scope,$uibModalInstance) {
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
                    $scope.condition.equalAmountName=condition.equalAmountName;
                    $scope.condition.equalAmountSource=condition.equalAmountSource;
                    EqualAmountPreviewService.saveEqualAmount($scope.condition).$promise.then(function (result) {
                        if (result.data == "success") {
                            toastr.success("创建成功!");
                        }
                    });
                })
            }



            //search
            $scope.search = function () {
                    EqualAmountPreviewService.postForm($scope.condition).$promise.then(function (result) {
                        $scope.rows = result.data;
                        if (!$scope.$$phase) {
                            $scope.$apply();
                        }
                        angular.forEach(result.data,function (k,v) {
                            $scope.sumPayMoney+=k.repaymentMoney;
                            $scope.sumInterest+=k.interest;
                        })
                        $scope.sumPayMoney/100;
                        $scope.sumInterest/100;
                    });


            };
        }
    ]
)