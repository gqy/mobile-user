/**
 * Created by Gqy on 14-8-28.
 */
cordova.define("com.test.plugin.MyToast", function(require, exports, module) {
    var exec = require('cordova/exec');
    module.exports = {

        /**
         * 一共5个参数
         第一个 :成功回调
         第二个 :失败回调
         第三个 :将要调用的类的配置名字(在config.xml中配置↑↑)
         第四个 :调用的方法名(一个类里可能有多个方法 靠这个参数区分)
         第五个 :传递的参数  以json的格式
         */
        show: function(message) {
            exec(null, null, "MyToast", "show", [message]);
        },
        showlong: function(message) {
            exec(null, null, "MyToast", "showlong", [message]);
        }
    };

});