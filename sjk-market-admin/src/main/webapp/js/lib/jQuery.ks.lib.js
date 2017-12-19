/*
* 功能:softmgr-v3-定义的常用jQuery方法,包括字符串处理
* 作者:luohuijun,
* 时间:2012年2月1日
*/
jQuery.extend({
	    /**********************************
	    *Fun：   判断对象是否为NULL
	    *Author：luohuijun 
	    *Params：target->判断对象
	    *Result: 返回true:为空，false:不为空
	    **********************************/
	    isNull: function (target) {
	        var result = false;
	        if ((!target) || target == null || target == "null" || target == "undefined") {
	            result = true;
	        } 
	        return result;
	    },
	    
	    /**********************************
	     *Fun：   判断对象是否为NULL和Empty
	     *Author：luohuijun 
	     *Params：target->判断对象
	     *Result: 返回true:为空，false:不为空
	     **********************************/
	     isNullOrEmpty: function (target) {
	         var result = false;
	         if (!target || target.length == 0) {
	             result = true;
	         }
	         return result;
	     },
	     
	     /**********************************
	      *Fun：   判断对象是否相等
	      *Author：luohuijun 
	      *Params：objA->对象A
	      *        objB->对象B
	      *Result: 返回true:相等，false:不等
	      **********************************/
	      equal: function (objA, objB) {
	          if (typeof arguments[0] != typeof arguments[1])
	              return false;

	          //数组
	          if (arguments[0] instanceof Array) {
	              if (arguments[0].length != arguments[1].length)
	                  return false;

	              var allElementsEqual = true;
	              for (var i = 0; i < arguments[0].length; ++i) {
	                  if (typeof arguments[0][i] != typeof arguments[1][i])
	                      return false;

	                  if (typeof arguments[0][i] == 'number' && typeof arguments[1][i] == 'number')
	                      allElementsEqual = (arguments[0][i] == arguments[1][i]);
	                  else
	                      allElementsEqual = arguments.callee(arguments[0][i], arguments[1][i]);            //递归判断对象是否相等                
	              }
	              return allElementsEqual;
	          }

	          //对象
	          if (arguments[0] instanceof Object && arguments[1] instanceof Object) {
	              var result = true;
	              var attributeLengthA = 0, attributeLengthB = 0;
	              for (var o in arguments[0]) {
	                  //判断两个对象的同名属性是否相同（数字或字符串）
	                  if (typeof arguments[0][o] == 'number' || typeof arguments[0][o] == 'string')
	                      result = eval("arguments[0]['" + o + "'] == arguments[1]['" + o + "']");
	                  else {
	                      //如果对象的属性也是对象，则递归判断两个对象的同名属性
	                      //if (!arguments.callee(arguments[0][o], arguments[1][o]))
	                      if (!arguments.callee(eval("arguments[0]['" + o + "']"), eval("arguments[1]['" + o + "']"))) {
	                          result = false;
	                          return result;
	                      }
	                  }
	                  ++attributeLengthA;
	              }

	              for (var o in arguments[1]) {
	                  ++attributeLengthB;
	              }

	              //如果两个对象的属性数目不等，则两个对象也不等
	              if (attributeLengthA != attributeLengthB)
	                  result = false;
	              return result;
	          }
	          return arguments[0] == arguments[1];
	      },
	      /**********************************
	       *Fun：   截取字符串
	       *Author：luohuijun 
	       *Params：target->目标字符串
	       *        start->开始截取位置
	       *        end->结束截取位置
	       *Result: 返回参数值
	       **********************************/
	       substring: function (target, start, end) {
	           var result = target.substring(start, end);
	           return result;
	       },
	       /**********************************
	        *Fun：  超过长度字符串截取，支持中文，字母。中文2字节、字母1字节
	        *Author：luohuijun 
	        *Params：target->目标字符串
	        *        slength->字节长度。 
	        *        tempStr->截取字符后用什么字符追加
	        *Result: 返回参数值
	        **********************************/
	        subStr: function (target, slength, tempStr) {

	            if (target != "" && target != null && target != undefined) {
	                var verify = target.split("");
	                var patrn = /[\u4E00-\u9FA5\uF900-\uFA2D]+$/;
	                var count = target.length;
	                var tempCount = 0;
	                for (var i = 0; i < count; i++) {
	                    if (patrn.test(verify[i])) {
	                        tempCount = tempCount + 2;
	                    } else {
	                        tempCount++;
	                    }
	                    if (tempCount >= slength) {
	                        target = $.substring(target, 0, i) + tempStr;
	                        break;
	                    }
	                }
	            }
	            return target;
	        },
	        /**********************************
	         *Fun：   去除字符串前后特殊字符
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        removeChar->去除的字符
	         *Result: 返回参数值
	         **********************************/
	         trimChar: function (target, removeChar) {
	             var sublen = 1;
	             var length = target.length;
	             var startChar = target.substring(0, 1);
	             var endChar = target.substring(length - 1, length);
	             if (startChar == removeChar) {
	                 target = target.substring(1, length);
	                 ++sublen;
	             }
	             if (endChar == removeChar) {
	                 target = target.substring(0, length - sublen);
	             }
	             return target;
	         },

	         /**********************************
	         *Fun：   去除字符串最前的特殊字符
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        removeChar->去除的字符
	         *Result: 返回参数值
	         **********************************/
	         trimStart: function (target, removeChar) {
	             var startChar = target.substring(0, 1);
	             if (startChar == removeChar) {
	                 target = target.substring(1, target.length);
	             }
	             return target;
	         },

	         /**********************************
	         *Fun：   去除字符串最后的特殊字符
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        removeChar->去除的字符
	         *Result: 返回参数值
	         **********************************/
	         trimEnd: function (target, removeChar) {
	             var length = target.length;
	             var endChar = tar.substring(1, length);
	             if (endChar == removeChar) {
	                 target = target.substring(0, length - 1);
	             }
	             return target;
	         },

	         /**********************************
	         *Fun：   根据分隔符转为数组
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        separtor->分隔符号
	         *Result: 返回参数值
	         **********************************/
	         split: function (target, separtor) {
	             return target.split(separtor);
	         },

	         /**********************************
	         *Fun：   判断开始字符
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        startString->开始字符
	         *Result: 返回true/false
	         **********************************/
	         startWith: function (target, startString) {
	             var result = false;
	             if (target.length > 0) {
	                 if (target.substring(0, 1) == startString) {
	                     target = true;
	                 }
	             }
	             return result;
	         },

	         /**********************************
	         *Fun：   判断结束字符
	         *Author：luohuijun 
	         *Params：target->目标字符串
	         *        endString->结束字符
	         *Result: 返回true/false
	         **********************************/
	         endWith: function (target, endString) {
	             var result = false;
	             var length = target.length;
	             if (length > 0) {
	                 if (target.substring(length - 1, 1) == endString) {
	                     target = true;
	                 }
	             }
	             return result;
	         },
});