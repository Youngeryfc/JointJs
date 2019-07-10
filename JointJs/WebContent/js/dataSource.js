var block_width = 50;
var block_height = 50;
var block_gap = 20;
var inner_gap = 10;
var position_x=0;
//节点分为装配体和零件两种
//同一个父节点下的所有零件节点，用一个方框装起来，零件节点的间距小
var nodeTypeAssembly = 1;
var nodeTypePart = 2;

//创建测试数据
function createTestData(maxLevel, maxChildrenSize) {

	var data = createTestDataNode(maxLevel, maxChildrenSize, "root", 1, 1);
	return data;

}

//递归函数
function createTestDataNode(maxLevel, maxChildrenSize, pid, currentLevel,
		currentIndex) {
	var data = {};
	var value=new Array('','','',1,'','','','','','','','');
	if (currentLevel > 1) {
		data.pid = pid;
		data.id = pid + '_' + currentIndex;
		data.level = currentLevel;
		data.self_x = 0;
		data.self_y = 0;
		data.node = currentIndex;
		data.border = false; //此处的border是指当前元素下的子元素是否需要加外边框
		data.border_x = 0; //此处的border是指当前元素下的子元素的外边框的x轴的位置
		data.border_y = 0; //此处的border是指当前元素下的子元素的外边框的y轴的位置
		data.border_width = 0;
		data.border_height = 0;
		data.value=value;
		//root
	} else {
		data.pid = "";
		data.id = "root";
		data.level = 1;
		data.self_x = 0;
		data.self_y = 0;
		data.border = false;
		data.border_x = 0; //此处的border是指当前元素下的子元素的外边框的x轴的位置
		data.border_y = 0; //此处的border是指当前元素下的子元素的外边框的y轴的位置
		data.border_width = 0;
		data.border_height = 0;
		data.bg_color="#9BBACF";
		data.value=value;
		data.type="ass";
		data.value0="";
		data.value1="";
		data.value2="";
		data.value3=1;
		data.value4="";
		data.value5="";
		data.value6="";
		data.value7="";
		data.value8="";
		data.value9="";
		data.value10="";
		data.value11="";
	}

	if (currentLevel < maxLevel) {
		var childrenSize = Math.floor(Math.random() * maxChildrenSize + 1);
		//childrenSize=3;
		var children = new Array();
		for (var i = 1; i <= childrenSize; i++) {
			//递归生成子节点
			var newChild = createTestDataNode(maxLevel, maxChildrenSize,
					data.id, currentLevel + 1, i);
			children.push(newChild);
		}
		//赋值给data
		data.children = children;
	}

	return data;

}

//新增一个对象
function createDataNode(root, id,bg_color,type,value) {
	//根据当前id查出当前的对象
	var data = findById(root, id);
	var children = {};
	if (data.children&&data.children.length>0) {
		children.pid = id;
		children.id = id + '_' + (data.children[data.children.length-1].node + 1);
		children.level = data.level + 1;
		children.self_x = 0;
		children.self_y = 0;
		children.node = data.children[data.children.length-1].node + 1;
		children.border = false; //此处的border是指当前元素下的子元素是否需要加外边框
		children.border_x = 0; //此处的border是指当前元素下的子元素的外边框的x轴的位置
		children.border_y = 0; //此处的border是指当前元素下的子元素的外边框的y轴的位置
		children.border_width = 0;
		children.border_height = 0;
		children.bg_color=bg_color;
		children.type=type;
		children.value=value;
		children.value0="";
		children.value1="";
		children.value2="";
		children.value3=1;
		children.value4="";
		children.value5="";
		children.value6="";
		children.value7="";
		children.value8="";
		children.value9="";
		children.value10="";
		children.value11="";
		data.children.push(children);
	} else {
		children.pid = id;
		children.id = id + '_' + 1;
		children.level = data.level + 1;
		children.self_x = 0;
		children.self_y = 0;
		children.node = 1;
		children.border = false; //此处的border是指当前元素下的子元素是否需要加外边框
		children.border_x = 0; //此处的border是指当前元素下的子元素的外边框的x轴的位置
		children.border_y = 0; //此处的border是指当前元素下的子元素的外边框的y轴的位置
		children.border_width = 0;
		children.border_height = 0;
		children.bg_color=bg_color;
		children.type=type;
		children.value=value;
		children.value0="";
		children.value1="";
		children.value2="";
		children.value3=1;
		children.value4="";
		children.value5="";
		children.value6="";
		children.value7="";
		children.value8="";
		children.value9="";
		children.value10="";
		children.value11="";
		data.children = [ children ];
	}
	return root;
}

//打印测试数据
function printData(data) {
	if (!data) {
		return;
	}

	//缩进处理
	var preBlank = "";
	for (var i = 0; i < data.level; i++) {
		preBlank = preBlank + "  ";
	}

	console.log(preBlank + 'id:' + data.id + ',node:' + data.node + ',level:'
			+ data.level + ',x:' + data.self_x + ',y:' + data.self_y
			+ ',border:' + data.border + ',border_x:' + data.border_x
			+ ',border_y:' + data.border_y + ',border_width:'
			+ data.border_width + ',border_height:' + data.border_height);
	if (data.children) {
		for (var i = 0; i < data.children.length; i++) {
			printData(data.children[i]);
		}
	}
}

//根据id，找对象
function findById(data, id) {
	if (data.id == id) {
		return data;
	}
	if (!data.children) {
		return null;
	}

	for (var i = 0; i < data.children.length; i++) {
		var child = findById(data.children[i], id);
		if (child) {
			return child;
		}
	}

	return null;
}

//查找当前最左最深的数据
function findLeftById(data, id) {
	if (data.id == id) {
		while (data.children) {
			if(data.children.length>0){
				id = id + "_1";
				data = findById(data, id);
			}else{
				return data;
			}
		}
		return data;
	}
}

//查找目录层次最深的对象,有多个时，返回最早发现的
function findMaxLevelData(data) {
	
	if (!data.children||data.children.length<1) {
		return data;
	}

	var findData;
	for (var i = 0; i < data.children.length; i++) {
		var child = findMaxLevelData(data.children[i]);
		if (!findData) {
			findData = child;
		} else {
			if (child.level > findData.level) {
				findData = child;
			}
		}
	}

	return findData;
}

//清除id,pid,level，测试用
function clearIdAndLevel(data) {
	data.border = false;
	data.border_width = 0;
	data.border_height = 0;
	data.border_x = 0;
	data.border_y = 0;
	data.self_x=0;
	data.self_y=0;
	position_x=0;
	if (!data.children) {
		return;
	}

	for (var i = 0; i < data.children.length; i++) {
		clearIdAndLevel(data.children[i]);
	}

}

//组装数据成标准tree格式
function resetIdAndLevel(data, pid, currentLevel, currentIndex) {
	if (!data) {
		return;
	}

	if (currentLevel > 1) {
		data.pid = pid;
		data.id = pid + '_' + currentIndex;
		data.level = currentLevel;
	} else {
		data.pid = "";
		data.id = "root";
		data.level = 1;
	}

	if (!data.children) {
		return;
	}

	//处理子节点
	for (var i = 0; i < data.children.length; i++) {
		resetIdAndLevel(data.children[i], data.id, currentLevel + 1, i + 1);
	}

}

var level = new Array();
//算法开始
var xx=0;
var root;     //最初的data数据集合
//根据root找到最左最深的元素
function leftLevel(croot) {
	debugger;
	root=croot;
	position_x=0;
	var left;
	if(root.children){
		left = findLeftById(root.children[0], root.children[0].id);
	}else{
		return root;
	}
	if(left.id=="root"){
		//当前元素只有一个
		return left;
	}
	var parent = findById(root, left.pid);
	getRoot(parent);
	select(parent);
	var child = findById(root, "root");
	return child;
}

function returnPosition_x() {
	return position_x;
}

function findParent(pdata) {
	pdata.self_x = pdata.children[0].self_x;
	pdata.self_y = (pdata.level - 1) * (block_height + block_gap);

	//找到上一级
	var parent = findById(root, pdata.pid);
	if (parent.children.length == 1) {
		findParent(parent);
	} else {
		return parent;
	}

}
//root节点下的特殊情况
function getRoot(parent){
	debugger;
	//记录没有子节点的节点
	var temparory_single_data = new Array();
	var temparory_data=new Array();
	if(parent.children&&parent.children.length>0){
		for(var i=0;i<parent.children.length;i++){
			if(parent.children[i].children){
				temparory_data.push(parent.children[i]);
				getRoot(parent.children[i]);
			}else{
				temparory_single_data.push(parent.children[i]);
			}
		}
		
		if(temparory_data.length>0&&temparory_single_data.length>0){
			sqrt(temparory_single_data);
			if(temparory_single_data.length>1){
				parent.self_x=(temparory_data[0].self_x+position_x-block_gap-block_width-inner_gap)/2;
			}else{
				parent.self_x=(temparory_data[0].self_x+temparory_single_data[0].self_x)/2;
			}
		}else if(temparory_data.length==0&&temparory_single_data.length>0){
			sqrt(temparory_single_data);
			if(temparory_single_data.length>1){
				parent.self_x=(position_x+temparory_single_data[0].self_x-block_width-inner_gap-block_gap)/2;
			}else{
				parent.self_x=temparory_single_data[0].self_x;
			}
		}else{
			parent.self_x=(temparory_data[0].self_x+temparory_data[temparory_data.length-1].self_x)/2;
		}
		parent.self_y=(parent.level-1)*(block_gap+block_height);
	}else{
		parent.self_x=position_x;
		parent.self_y=(parent.level-1)*(block_gap+block_height);
		position_x=position_x+block_width+block_gap;
	}
	return parent;
}



function select(cdata) {
	debugger;
	if (cdata.id != "root") {
		var parent = findById(root, cdata.pid);
		//if(parent.children){
			if (parent.children.length == 1) {
				parent = setParent(parent);
			} else {
				parent = allRight(parent);
				if (parent.id == "root") {
					return parent;
				} else {
					select(parent);
				}
			}
		//}
	} else {
		return cdata;
	}
}

function setParent(cdata) {
	debugger;
	var rootdata;
	//设置当前元素的位置
	cdata.self_x = cdata.children[0].self_x;
	cdata.self_y = (cdata.level - 1) * (block_gap + block_height);
	//寻找当前元素的上一级
	if (cdata.id == "root") {
		return cdata;
	} else if (cdata.pid == 'root') {
		var parent = findById(root, cdata.pid);
		if(parent.children.length==1){
			setParent(parent);
		}else{
			//rootdata = allRight(cdata);
			rootdata = allRight(parent);
		}
		return rootdata;
	} else {
		select(cdata);
		return cdata;
	}

}


//此处的c是来判断是否是从root下新开的一个节点
function allRight(parent) {
	debugger;
	var temparory_single_data = new Array();
	var temparory_data=new Array();
	for(var i=1;i<parent.children.length; i++){
		if(parent.children[i].children){
			temparory_data.push(parent.children[i]);
			getRoot(parent.children[i]);
		}else{
			temparory_single_data.push(parent.children[i]);
		}
	}
	
	if(temparory_data.length>0&&temparory_single_data.length>0){
		sqrt(temparory_single_data);
		if(temparory_single_data.length>1){
			parent.self_x=(parent.children[0].self_x+position_x-block_gap-block_width-inner_gap)/2;
		}else{
			parent.self_x=(parent.children[0].self_x+temparory_single_data[0].self_x)/2;
		}
	}else if(temparory_data.length==0&&temparory_single_data.length>0){
		sqrt(temparory_single_data);
		if(temparory_single_data.length>1){
			parent.self_x=(parent.children[0].self_x+position_x-block_gap-block_width-inner_gap/2)/2;
		}else{
			parent.self_x=(parent.children[0].self_x+temparory_single_data[0].self_x)/2;
		}
	}else if(temparory_data.length>0&&temparory_single_data.length==0){
		parent.self_x=(parent.children[0].self_x+temparory_data[temparory_data.length-1].self_x)/2;
	}else{
		return parent;
	}
	
	
	parent.self_y=(parent.level-1)*(block_gap+block_height);
	return parent;
}



//开平方的位置
function sqrt(data) {
	//找到父级元素
	var parent = findById(root, data[0].pid);
	var r = Math.ceil(Math.sqrt(data.length));
	var px;
	var py;
	if(data.length==1){
		data[0].self_x=position_x;
		data[0].self_y=(block_height + block_gap) * (data[0].level - 1);
		position_x=position_x+block_gap+block_width;
	}else{
		for (var i = 0; i < data.length; i++) {
			var inner_r = Math.ceil(Math.sqrt(i+1));
			if (i < 2) {
				data[i].self_x = inner_gap * (i + 1) + block_width * i + position_x;
				data[i].self_y = (block_height + block_gap) * (data[i].level - 1)
						+ inner_gap;
			} else {
				if (inner_r * inner_r - (i + 1) < inner_r) {
					py = inner_r;
					px = (i + 1) - inner_r * (inner_r - 1);
				} else {
					px = inner_r;
					py = (i + 1) - (inner_r - 1) * (inner_r - 1);
				}
				data[i].self_x = (px - 1) * block_width + inner_gap * px
						+ position_x;
				data[i].self_y = (py - 1) * block_height + inner_gap * py
						+ (block_height + block_gap) * (data[i].level - 1);
			}
		}
		parent.border = true;
		parent.border_x = data[0].self_x - inner_gap;
		parent.border_y = data[0].self_y - inner_gap;
		parent.border_width = r * block_width + (r + 1) * inner_gap;
		if (r * r - data.length < r) {
			parent.border_height = r * block_height + (r + 1) * inner_gap;
		} else {
			parent.border_height = (r - 1) * block_height + r * inner_gap;
		}
		position_x = position_x+r*block_width+(r+1)*inner_gap+block_gap;
	}
	
	return position_x;
}