

/*判断对象是否为空*/
function isEmptyForString(obj) {

    if (obj != null && obj != "" && obj.length != 0 && obj.trim() != "" && obj.trim().length != 0){
        return false;   //不为空
    }else {
        return true;  //为空
    }
}