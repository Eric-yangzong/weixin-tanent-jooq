void function (global) {
    var extend,
        _extend,
        _isObject;

    _isObject = function (o) {
        return Object.prototype.toString.call(o) === '[object Object]';
    }

    _extend = function self(destination, source) {
        var property;
        for (property in destination) {
            if (destination.hasOwnProperty(property)) {

                // 若destination[property]和sourc[property]都是对象，则递归
                if (_isObject(destination[property]) && _isObject(source[property])) {
                    self(destination[property], source[property]);
                };

                // 若sourc[property]已存在，则跳过
                if (source.hasOwnProperty(property)) {
                    continue;
                } else {
                    source[property] = destination[property];
                }
            }
        }
    }

    extend = function () {
        var arr = arguments,
            result = {},
            i;

        if (!arr.length) return {};

        for (i = arr.length - 1; i >= 0; i--) {
            if (_isObject(arr[i])) {
                _extend(arr[i], result);
            };
        }

        arr[0] = result;
        return result;
    }

    global.extend = extend;
}(window)

var queryEnum = {
    EOperate: {
        Equals: 1,
        NotEquals: 2,
        Like: 3,
        Great: 4,
        GreateEquals: 5,
        Less: 6,
        LessEquals: 7,
        NotLike: 8,
        IsNull: 9,
        IsNotNull: 10,
    },
    EOrder: {
        asc: 1,
        desc: 2
    },
    ERelation: {
        and: 1,
        or: 2
    }
}

function queryFilter(fieldName, fieldValue, operate) {
    if (operate == undefined) {
        operate = 1;//
    }
    this.relation = 1;//and
    this.field = null;
    this.value = null;
    this.op = operate;
    this.Add = function (filter) {
        if (this.querys == null) {
            this.querys = new Array();
        }
        filter = extend(new queryFilter(), filter);
        if (filter.OperateMode == 10 || filter.OperateMode == 11) {
            filter.value = "0"; //仅做占位符。
        }
        this.querys.push(filter);
    }
    if (fieldName && fieldName.length > 0) {
        this.field = fieldName;
        this.value = fieldValue != null ? fieldValue : "";
    }
}

function orderField(fieldName, orderType) {
    this.fieldName = null;
    this.order = 1;
    this.Add = function (order) {
        if (this.orders == null) {
            this.orders = new Array();
        }
        order = extend(new orderField(), order);
        this.orders.push(order);
    }

    if (fieldName && fieldName.length > 0) {
        this.fieldName = fieldName;
        this.order = orderType != null ? orderType : "1";
    }
}

function queryPage(queryFilter, orderField) {
    this.page = 1;//所在页
    this.size = 10;//每页显示行数
    this.querys = new Array();
    this.orders = new Array();

    if (queryFilter && queryFilter.querys) {
        this.querys = queryFilter.querys;
    }

    if (orderField && orderField.orders) {
        this.orders = orderField.orders;
    }
    
    this.queryURI = function(){
        return encodeURI(JSON.stringify(this));
    } 


}