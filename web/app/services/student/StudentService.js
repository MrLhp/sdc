(function (angular) {
    function StudentService($resource,UrlConfigService) {
        this._schema = [
            {name:'schoolName',label:'学校名称',sortable:true},
            {name:'name',label:'姓名',sortable:true},
            {name:'grade',label:'年级',sortable:true},
            {name:'graduate',label:'毕业',sortable:true},
            {label: '操作', sortable: false, width: 240, type: 'template', templateUrl: 'operation.html'}
        ];

        this._searchListUrl = UrlConfigService.urlConfig.student.searchListUrl;

        this._sort = 'name';
        this._order = 'asc';

        BaseListService.call(this, this._searchListUrl, this._url, $resource, this._schema);


    }

    StudentService.prototype = new BaseListService().prototype;
    StudentService.$inject = ['$resource','UrlConfigService'];
    angular.module('MetronicApp').service('StudentService',StudentService);
})(angular);