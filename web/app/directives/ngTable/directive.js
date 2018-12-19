/**
 *  电影项目自定义列表
 */
'use strict';
angular.module("MetronicApp").directive('ngTable', ['$timeout', function ($timeout) {
    return {
        templateUrl: 'app/directives/ngTable/template.html',
        restrict: 'EA',
        scope: {
            a: '=',
            rowsOne: '=',
            columns: '=',
            order: '=',
            pageable: '=',
            rows: '=',
            notpageable: '=',
            notheadpageable:'=',
            embed: '=',
            pageableConfig: '='
        },
        replace: true,
        link: function ($scope, $element, $attrs) {
        },
        controller: ['$scope', function ($scope) {
            $(window).scroll(function () {
                if ($(".theadFake").length == 0) {
                    return;
                }
                $(".theadFake").css({
                    "width": $(".table-scrollable-id")[0].offsetWidth + "px",
                    // "left":$(".page-sidebar-menu").width() + 41 + "px",
                    "top": 40 + "px"
                })
                $(".table-id").css({
                    "width": $(".tableMain").width() + "px"
                });

                if (navigator.userAgent.indexOf("Chrome") > -1) {
                    if ($(".thead-float-tr").children().length != 0 && $(".thead-main-tr").children().length != 0) {
                        for (var i = 0; i < $(".thead-float-tr").children().length; i++) {
                            if (i == 0 || i == $(".thead-float-tr").children().length - 1) {
                                $($(".thead-float-tr").children()[i]).css({
                                    "padding-left": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 0.5,
                                    "padding-right": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 0.5
                                })
                            } else {
                                $($(".thead-float-tr").children()[i]).css({
                                    "padding-left": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 1,
                                    "padding-right": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 1
                                })
                            }
                        }
                    }
                } else {
                    if ($(".thead-float-tr").children().length != 0 && $(".thead-main-tr").children().length != 0) {
                        for (var i = 0; i < $(".thead-float-tr").children().length; i++) {
                            $($(".thead-float-tr").children()[i]).css({
                                "padding-left": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 0.5,
                                "padding-right": ( (  $($(".thead-main-tr").children()[i])[0].offsetWidth - $($(".thead-float-tr").children()[i]).width()) / 2 ) - 0.5
                            })
                        }
                    }
                }

                if ($(window).scrollTop() > ($(".thead").offset().top - 50)) {
                    if (navigator.userAgent.indexOf("Firefox") > -1) {
                        $(".thead-float").css({
                            "display": "-moz-box"
                        })
                        $(".theadFake").css({
                            "display": "block"
                        })
                    } else if (navigator.userAgent.indexOf("Chrome") > -1) {
                        $(".thead-float").css({
                            "display": "block"
                        })
                        $(".theadFake").css({
                            "display": "block"
                        })
                    } else {
                        $(".thead-float").css({
                            "display": "-ms-flexbox"
                        })
                        $(".theadFake").css({
                            "display": "-ms-grid"
                        })
                    }
                } else {
                    $(".thead-float").css({
                        "display": "none"
                    })
                    $(".theadFake").css({
                        "display": "none"
                    })

                }
            });
            $(".table-scrollable").scroll(function (e) {
                $(".thead-float").css({
                    "margin-left": 0 - e.currentTarget.scrollLeft + "px"
                });
            })
            //获取返回的能看到的按钮列表
            $scope.buttonKeys = $scope.$parent.buttonKeys;

            if ($scope.$parent) {
                $scope.parentObj = $scope.$parent;
            }
            if ($scope.embed) { // 保存子表格元素，防止刷新后丢失
                $scope.tablec = $('.embed-table');
            }
            //判断是否直接输入
            $scope.isOutput = function (column) {
                return !(undefined !== column['type'] || undefined !== column['filter'])
            };
            //获取列显示类型
            $scope.typeOf = function (column) {
                return column['type']
            };

            // checkbox is checked
            $scope.onCheckboxChecked = function (row) {
                if (row.chk) {
                    $scope.$parent.checkedList.push(row);
                } else {
                    angular.forEach($scope.$parent.checkedList, function (v, index) {
                        if (v.$$hashKey == row.$$hashKey) {
                            $scope.$parent.checkedList.splice(index, 1);
                        }
                    })
                }
            };

            $scope.onRadioChecked = function ($event, row) {
                if ($event.target.checked) {
                    $scope.$parent.row = row;
                }
            };

            $scope.onActionClick = function (row, parentMethod) {
                if ($scope.$parent.hasOwnProperty(parentMethod)) {
                    $scope.$parent[parentMethod](row);
                }
            };

            $scope.isNullString = function (str) {
                return !(str && str !== "");
            };

            $scope.isEmpty = function (array) {
                return !(array && array.length);
            };
            // sort and order
            $scope.isSortedBy = function (columnName) {
                return $scope.sort === columnName;
            };

            $scope.isOrder = function (order) {
                return $scope.order === order;
            };

            $scope.onRowClicked = function (row, $event) {
                if (!$scope.embed){
                    if ($scope.$parent.hasOwnProperty('onRowClicked')) {
                        $scope.$parent['onRowClicked'](row,$event);
                    }
                }else{
                    if ($($event.currentTarget).next('tr.embed-table').size() > 0 && !$('.embed-table').is(":hidden")) {
                        $('.embed-table').hide();
                        return;
                    }
                    if ($scope.$parent.hasOwnProperty('onRowClicked')) {
                        $scope.$parent['onRowClicked'](row, $scope.callbackfn);
                    }
                    $scope.tablec.insertAfter($event.currentTarget);    //移动节点
                    $('.embed-table').show();
                }

            }
        }]
    };
}]).filter("ngTableFilter", ['$filter', '$sce', function ($filter, $sce) {
    return function (val, filter) {
        return $filter(filter)(val);
    }
}]);

