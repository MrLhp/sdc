/**
 * 基础（枚举）数据存储服务
 * Created by wangwj on 2016/6/4.
 */
angular.module("MetronicApp").service("EnumService", function () {
    this._data = {
        /*性别*/
        "genderType": [
            {"key": "male", "text": "男"},
            {"key": "female", "text": "女"}
        ],
        /*贷款类型*/
        "equalAmountTypes":[
            {"key":"interest","text":"等额本息"},
            {"key":"principal","text":"等额本金"}
        ],
        /*贷款来源*/
        "equalAmountSources":[
            {"key":"jdGoldBar","text":"京东金条"},
            {"key":"jdWhiteBar","text":"京东白条"},
            {"key":"antBorrow","text":"蚂蚁借呗"},
            {"key":"antFlower","text":"蚂蚁花呗"},
            {"key":"cmbCreditCard","text":"招商信用卡"}
        ]
    };
    this.get = function (key) {
        return this._data[key];
    }
});