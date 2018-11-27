(function (angular) {
    function EqualAmountPreviewService($resource,UrlConfigService) {
        this._schema = [
            {name:'no',label:'序号',sortable:true},
            {name:'days',label:'周期',sortable:true},
            {name:'repaymentDate',label:'还款日',sortable:true,filter:"timestampToDate"},
            {name:'repaymentMoney',label:'还款额度',sortable:true,filter: "number2Divide"},
            {name:'principal',label:'本金',sortable:true,filter: "number2Divide"},
            {name:'interest',label:'利息',sortable:true,filter: "number2Divide"},
            {name:'amountMoney',label:'剩余待还',sortable:true,filter: "number2Divide"},
        ];

        this._searchListUrl = UrlConfigService.urlConfig.equalAmount.preview.searchPreviewUrl;
        this._saveEqualAmountUrl = UrlConfigService.urlConfig.equalAmount.preview.saveEqualAmountUrl;

        this._sort = 'no';
        this._order = 'asc';

        BaseListService.call(this, this._searchListUrl, this._url, $resource, this._schema);

        this.saveEqualAmount=function (condition) {
            return this.$resource(this._saveEqualAmountUrl,null, {'save': {method: 'POST'}}).save(condition);
        }

    }

    EqualAmountPreviewService.prototype = new BaseListService().prototype;
    EqualAmountPreviewService.$inject = ['$resource','UrlConfigService'];
    angular.module('MetronicApp').service('EqualAmountPreviewService',EqualAmountPreviewService);
})(angular);

(function (angular) {
    function EqualAmountListService($resource,UrlConfigService) {
        this._schema = [
            {name:'username',label:'用户名称',sortable:true},
            {name:'equalAmountName',label:'贷款名称',sortable:true},
            {name:'amountTotalMoney',label:'总待还金额',sortable:true,filter: "number2Divide"},
            {name:'equalAmountType',label:'贷款类型',sortable:true,filter:"equalAmountTypesFilter"},
            {name:'equalAmountSource',label:'贷款来源',sortable:true,filter:"equalAmountSourcesFilter"},
            {label: '操作', sortable: false, width: 240, type: 'template', templateUrl: 'operation.html'}
        ];

        this._searchListUrl = UrlConfigService.urlConfig.equalAmount.statistics.searchListUrl;

        this._sort = 'no';
        this._order = 'asc';

        BaseListService.call(this, this._searchListUrl, this._url, $resource, this._schema);

    }

    EqualAmountListService.prototype = new BaseListService().prototype;
    EqualAmountListService.$inject = ['$resource','UrlConfigService'];
    angular.module('MetronicApp').service('EqualAmountListService',EqualAmountListService);
})(angular);

(function (angular) {
    function EqualAmountDetailService($resource,UrlConfigService) {
        this._schema = [
            {name:'no',label:'序号',sortable:true},
            {name:'days',label:'周期',sortable:true},
            {name:'repaymentDate',label:'还款日',sortable:true,filter:"timestampToDate"},
            {name:'repaymentMoney',label:'还款额度',sortable:true,filter: "number2Divide"},
            {name:'principal',label:'本金',sortable:true,filter: "number2Divide"},
            {name:'interest',label:'利息',sortable:true,filter: "number2Divide"},
            {name:'amountMoney',label:'剩余待还',sortable:true,filter: "number2Divide"},
            {name:'isPayment',label:'已还款',sortable:true}
        ];

        this._searchListUrl = UrlConfigService.urlConfig.equalAmount.preview.searchPreviewUrl;

        this._sort = 'no';
        this._order = 'asc';

        BaseListService.call(this, this._searchListUrl, this._url, $resource, this._schema);


    }

    EqualAmountDetailService.prototype = new BaseListService().prototype;
    EqualAmountDetailService.$inject = ['$resource','UrlConfigService'];
    angular.module('MetronicApp').service('EqualAmountDetailService',EqualAmountDetailService);
})(angular);
