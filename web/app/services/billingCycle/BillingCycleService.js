(function (angular) {
    function BillingCycleService($resource,UrlConfigService) {
        this._schema = [
            {name:'name',label:'账单名称',sortable:true},
            {name:'billingCreateDate',label:'账单产生时间',sortable:true,filter:"timestampToDate"},
            {name:'billingMoney',label:'账单金额',sortable:true,filter:"number2Divide"},
            {name:'billingType',label:'账单类型',sortable:true,filter:"billingTypesFilter"},
            {name:'useTo',label:'用途',sortable:true},
            {name:'description',label:'描述',sortable:true},
            {name:'isFinish',label:'已结清',sortable:true,filter:"isFinishFilter"},
            {label:'操作',sortable:true,type: 'template',templateUrl:"operation.html",width:200}
        ];
        this._schemaDetail = [
            {name:'paymentBillDate',label:'支付时间',sortable:true,filter:"timestampToDate"},
            {name:'paymentMoney',label:'支付金额',sortable:true,filter:"number2Divide"},
            {name:'description',label:'描述',sortable:true},
            {name:'createdDate',label:'创建时间',sortable:true,filter:"timestampToDate"}
        ];
        this._searchListUrl = UrlConfigService.urlConfig.billingCycle.searchListUrl;
        this._url = UrlConfigService.urlConfig.billingCycle.url;

        this._searchRecordsListUrl = UrlConfigService.urlConfig.billingRecords.searchListUrl;
        this._saveUrl = UrlConfigService.urlConfig.billingRecords.saveUrl;

        this._sort = 'no';
        this._order = 'asc';

        BaseListService.call(this, this._searchListUrl, this._url, $resource, this._schema);

        this.getDetailSchema = function () {
            return this._schemaDetail;
        };

        this.getRecordList = function (id) {
            let resource = $resource(this._searchRecordsListUrl,{id:id}, {'getRecordList': {method: 'POST'}});
            return resource.getRecordList(id);
        };

        this.saveBillingRecord = function (record) {
            let resource = $resource(this._saveUrl);
            return resource.save(record);
        };

    }

    BillingCycleService.prototype = new BaseListService().prototype;
    BillingCycleService.$inject = ['$resource','UrlConfigService'];
    angular.module('MetronicApp').service('BillingCycleService',BillingCycleService);
})(angular);

