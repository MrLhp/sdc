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
        "equalAmountType":[
            {"key":"interest","text":"等额本息"},
            {"key":"principal","text":"等额本金"}
        ]
    };
    this.get = function (key) {
        return this._data[key];
    }
});