  
//遍历data
function forData(data){
  getTable(data);
  if(data.children&&data.children.length>0){
      for(var i=0;i<data.children.length;i++){
        forData(data.children[i]);
      }
  }
}

function getTable(obj){
      var str='<tr>';
      str=str+'<td width="100px;"><input type="text" name="node" style="width:95px;" value='+obj.id+'></td>';
      str=str+'<td width="150px;"><input type="text" name="bh" class="bh" style="width:145px;" value='+(typeof(obj.value[0]) == "undefined"?null:obj.value[0])+'></td>';
      str=str+'<td width="100px;"><input type="text" name="name" style="width:105px;" value='+(typeof(obj.value[1]) == "undefined"?null:obj.value[1])+'></td>';
      str=str+'<td width="50px;"><input type="text" name="yname" class="yname" style="width:105px;" value='+(typeof(obj.value[2]) == "undefined"?null:obj.value[2])+'></td>';
      str=str+'<td width="50px;"><input type="text" name="num" style="width:48px;" value='+(typeof(obj.value[3]) == "undefined"?null:obj.value[3])+'></td>';
      str=str+'<td width="100px;"><input type="text" name="config" style="width:95px;" value='+obj.value[4]+'></td>';
      str=str+'<td width="100px;"><input type="text" name="th" style="width:95px;" value='+(typeof(obj.value[5]) == "undefined"?null:obj.value[5])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="mh" style="width:75px;" value='+(typeof(obj.value[6]) == "undefined"?null:obj.value[6])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="nick" style="width:75px;" value='+(typeof(obj.value[7]) == "undefined"?null:obj.value[7])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="source" style="width:75px;" value='+(typeof(obj.value[8]) == "undefined"?null:obj.value[8])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="remark" style="width:75px;" value='+(typeof(obj.value[9]) == "undefined"?null:obj.value[9])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="length" style="width:75px;" value='+(typeof(obj.value[10]) == "undefined"?null:obj.value[10])+'></td>';
      str=str+'<td width="80px;"><input type="text" name="dnum" style="width:75px;" value='+(typeof(obj.value[11]) == "undefined"?null:obj.value[11])+'></td>';
      str=str+'<td width="80px;"><input type="button" id="table_search" onclick="table_search(this)" value="搜索"></td>';
      str=str+'</tr>';
      $("#tb").append(str);
}
  
//确定按钮

$("#submit").click(function(){
	confirm=true;
  $("#tb tr").each(function(i){  //遍历tr
    var value=new Array(11);
    $(this).children('td').each(function(j){  // 遍历 tr 的各个 td
      if(j>0){
        value[j-1]=$(this).find("input").val();
      }
    });
    
    //获取每一行的第一列的值(data的id)
    if(i>0){
      var id = $(this).children('td').eq(0).find("input").val();
      var node=findById(data,id);
      node.value=value;
    }
  });
  //清除原样式
  graph.clear();
  alert("修改成功！！！");
})

//取消按钮
$("#cancel").click(function(){
  //清空所有input的值
  $("tr td").remove();
  forData(data);
  alert("取消成功！");
})