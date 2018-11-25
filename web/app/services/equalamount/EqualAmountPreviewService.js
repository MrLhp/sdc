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

        this._searchListUrl = UrlConfigService.urlConfig.equalAmount.searchPreviewUrl;
        this._saveEqualAmountUrl = UrlConfigService.urlConfig.equalAmount.saveEqualAmountUrl;

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