angular.module("MetronicApp").controller('StudentController',
    ['$rootScope', '$scope', '$location', '$uibModal', "toastr", 'StudentService',
        function ($rootScope, $scope, $location, $uibModal, toastr, StudentService) {
            $scope.$on('$viewContentLoaded', function () {
                // initialize core components
                App.initAjax();
                $rootScope.settings.layout.pageBodySolid = true;
                $rootScope.settings.layout.pageSidebarClosed = false;
            });

            //ng-table about
            $scope.columns = StudentService.getSchema();
            $scope.sort = StudentService.getSort();
            $scope.order = StudentService.getOrder();
            $scope.pageable = StudentService.getPageable();
            $scope.selectable = true;

            //method of fetch list data
            $scope.list = function () {
                StudentService.list().$promise.then(function (result) {
                    $scope.rows = result.data;

                    /**实现默认选中*/
                    for (var i = 0; i < $scope.rows.length; i++) {
                        if ($scope.rows[i].id == 1) {
                            $scope.rows[i].selected = true
                        }
                    }
                    //storage page state
                    StudentService.setStoredPage(result.pageable.number);
                    $scope.pageable = result.pageable;
                    if (!$scope.$$phase) {
                        $scope.$apply();
                    }
                });
            };

            var gotoFirstPage = function () {
                StudentService.setStoredPage(0);
                $scope.list();
            };

            //start to fetch list data
            $scope.condition = {};
            StudentService.clearSearchParams();
            gotoFirstPage();

            //fetch data when the size of page changed
            $scope.$watch('pageable.size', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                StudentService.setSize(newVal);
                gotoFirstPage();
            });

            //fetch data when the number of pages changed
            $scope.$watch('pageable.number', function (newVal, oldVal) {
                if (newVal == oldVal) return;
                StudentService.setStoredPage(newVal);
                $scope.list();
            });

            $scope.$watch('sort', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                StudentService.setSort(newValue);
                $scope.list();
            });

            $scope.$watch('order', function (newValue, oldValue) {
                if (oldValue == newValue) return;
                StudentService.setOrder(newValue);
                $scope.list();
            });

            $scope.create = function () {
                $location.path("/student/create.html").search({});
            };

            //search
            $scope.search = function () {
                StudentService.putSearchParams(
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
        }
    ]
)