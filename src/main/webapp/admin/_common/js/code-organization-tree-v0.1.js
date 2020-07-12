// HashMap을 생성
// 넣기 : bjmMap.put(키, 값)
// 얻기 : bjmMap.get(키)
var bjmMap = new JavaMap();

// 생성자
Tree = function( tree_id, label , lv1, lv2, lv3, idx, del_yn ) {
    this.tree_id = tree_id;
    this.label = label;
    this.visible = false;
    this.nodes = new Array();
    this.nodes_count = 0;
    this.lv1 = lv1;
    this.lv2 = lv2;
    this.lv3 = lv3;
    this.idx = idx;
    this.del_yn = del_yn;
    this.parent_treeid = "";
    
    // 노드를 맵에 등록
    bjmMap.put(tree_id, this);
    //console.log("Tree new Add: "+ JSON.stringify( this ) );
}
// 자식 노드 추가 
Tree.prototype.add = function( child_tree ) {
    this.nodes[child_tree.tree_id] = child_tree;
    child_tree.parent_treeid = this.tree_id;
    this.nodes_count++;
    if(this.nodes_count > 0) {
    	this.label = this.label;
    }
}
// 노드 삭제
Tree.prototype.drop = function( node_id ) {
    delete this.nodes[node_id];
    this.nodes_count--;
}
// 노드의 속성값 리턴
Tree.prototype.info = function() {
	return '{id:'+this.tree_id+', name:'+(this.label.replace(/\'\"/gi,""))+', lv1:'+this.lv1+', lv2:'+this.lv2+', lv3:'+this.lv3+'", idx='+this.idx+', parentid='+this.parent_treeid+', children='+this.nodes_count+', nodes.length='+this.nodes.length+', del_yn='+this.del_yn+' }';
}
// 노드 리턴
Tree.prototype.get = function(node_id) {
	var fNode = this.nodes[node_id];
	return fNode;
}
// 노드의 속성 data tag 리턴
Tree.prototype.tagAttrs = function() {
	return ' data-treeid="'+this.tree_id+'" data-label="'+(this.label.replace(/\'\"/gi,""))+'" data-lv1="'+this.lv1+'" data-lv2="'+this.lv2+'" data-lv3="'+this.lv3+'" data-parentid="'+this.parent_treeid+'" data-children='+this.nodes_count+' data-delyn='+this.del_yn+' ';
}
// 화면 출력
Tree.prototype.print = function() {
	var attr = "", del_yn="", del_yn_txt="";
    document.write( "<table border=0 cellpadding=0 cellspacing=0>" );
    if( this.nodes_count > 0 ){
        attr = this.tagAttrs();
        document.write( "<tr><td><span onClick=\"onClickNode('" + this.tree_id + "_node', this, "+this.nodes_count+")\" style=\"cursor:hand\" "+attr+"><b>" + this.label + "</b></span></td></tr>" );
        document.write( "<tbody id='" + this.tree_id + "_node' style='display:" + ( this.visible ? "inline" : "none" ) + "'>" );
        document.write( "<tr><td style='padding-left:20px'>" );
        for( var node_id in this.nodes ) {
            node = this.nodes[node_id];
            node.print();
        }
        document.write( "</td></tr>" );
        document.write( "</tbody>" );
    } else{
        attr = this.tagAttrs();
        if(this.del_yn=="Y") {
        	del_yn=" class=\"text-warning\" "; del_yn_txt="[삭제됨]";
    	} else {
    		del_yn=""; del_yn_txt="";
        }
        document.write( "<tr><td><span onClick=\"onClickNode('" + this.tree_id + "_node', this, "+this.nodes_count+")\" "+attr+" "+del_yn+">" + this.label + del_yn_txt + "</span></td></tr>" );
    }
    document.write( "</table>" );
}




//==========================================
//    추가 유틸
//==========================================

// 특수문자제거
function specialCharRemove(obj) {
	var val = obj.value;
	var pattern = /[^(가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9)]/gi;   // 특수문자 제거
	//var pattern = /[^(0-9)]/gi;   // 숫자이외는 제거
	if(pattern.test(val)) {
		obj.value = val.replace(pattern,"");
	}
	return obj.value;
}
//
function getTagAttr(tree_id, label, lv1, lv2, lv3) {
	return ' data-treeid="'+tree_id+'" data-label="'+(label.replace(/\'\'/gi,""))+'" data-lv1="'+lv1+'" data-lv2="'+lv2+'" data-lv3="'+lv3+'" ';
}
// 부모 treeid 리턴
function getParentTreeidByLevel(lv1, lv2, lv3) {
	return (lv2=="" ? "ROOT" : (lv3=="" ? lv1 : lv1+lv2) );
}
// 부모 treeid 리턴
function getParentTreeidByTreeid(treeid) {
	var l = treeid.length;
	switch(l) {
		case 2: return "ROOT";
		case 4: return treeid.substring(0,2);
		case 7: return treeid.substring(0,4);
	}
	return "";
}
// 하부 노드 보이기/감추기
function onClickNode( node_id , treeObj, childrens ){
    
    try {
    	var node = document.getElementById(node_id);
    	if(node_id != "ROOT") {
	        if( node.style.display == "none" ) {
	            node.style.display = "inline";
	        } else{
	            node.style.display = "none";
	        }
    	}
    } catch ( err ) { var errmsg = "Error:\n"; for (var i in err) { errmsg += i + ' : ' + err[i] + '\n'; } errmsg += "\n\nSource:"+String(treeObj.outerHTML); /*alert( errmsg ); */ }
    
    
    // 추가 메뉴에 데이터 저장하기
    var parentTreeid = getParentTreeidByLevel( $(treeObj).data("lv1"), $(treeObj).data("lv2"), $(treeObj).data("lv3") );
    $("#popupContextMenu").show();
    $("#popupContextMenu").data("parentTreeid",  ""+parentTreeid);
    $("#popupContextMenu").data("treeid", ""+(parseInt(parentTreeid).length/2));
    
    // form 에 데이터 세팅 for del,modify,insert
    var treeid   = $(treeObj).data("treeid");
    var treeNode = bjmMap.get(treeid);
    var p_p_node_name = "";
    var p_node_name   = "";

    console.log("===============debug start..["+treeid+"]");
    if(treeid!="ROOT") {
    	console.log("1)"+ $(treeObj));
    	//console.log("2)"+ $(treeObj).data("lv1"));
    	//console.log("3)"+ JSON.stringify(treeNode));
    	
    	p_p_node_name = bjmMap.get($(treeObj).data("lv1")).label ;
    	p_node_name   = $(treeObj).data("lv3")=="000" ?  "" : " > "+bjmMap.get($(treeObj).data("lv1")+""+$(treeObj).data("lv2")).label;
    }
    
    console.log("[1]"+ treeid );
    console.log("[2]"+ treeNode.info() );
    
    
    $("#org_parent_name").html( p_p_node_name + p_node_name  );//label,상위조직명
    $("#org_idx").val( treeNode.idx );//input form,조직명
    $("#org_name").val( treeNode.label );//input form,조직명
    $("#org_lv1").val( treeNode.lv1 );//hidden form
    $("#org_lv2").val( treeNode.lv2 );//hidden form
    $("#org_lv3").val( treeNode.lv3 );//input form,3레벨코드
    $("#span_orglv1").html( treeNode.lv1 );//label
    $("#span_orglv2").html( treeNode.lv2 );//label
    
    $("#selected_org_info").data("tree_id",treeNode.tree_id);
    $("#selected_org_info").data("label",treeNode.label);
    $("#selected_org_info").data("childrens",treeNode.nodes_count);
    $("#selected_org_info").data("lv1",treeNode.lv1);
    $("#selected_org_info").data("lv2",treeNode.lv2);
    $("#selected_org_info").data("lv3",treeNode.lv3);
    $("#selected_org_info").data("idx",treeNode.idx);
    $("#selected_org_info").data("del_yn",treeNode.del_yn);
    $("#selected_org_info").data("parent_treeid",treeNode.parent_treeid);
    
    if(treeNode.del_yn=="Y") {
    	$("#btnDel").html("복원");
    } else {
    	$("#btnDel").html("삭제");
    }
    
    var _x = "[Debug]\n";
        //_x = String(treeObj.outerHTML) + "\n\n childrens = " + childrens + " => " + $(treeObj).data("treeid");
        _x += $(treeObj).data("treeid");
        _x += "\n menu3 data-treeid="+ $("#menu3").data("treeid");
    //_x = $(treeObj).info();
        _x = "";
    if( _x != "" ) alert( _x );
    
    // 숨겨진 모든 버튼 보이게 , added by 17-11-01
    try { $("#btnUpd").show(); } catch ( e ) {}
    try { $("#btnDel").show(); } catch ( e ) {}
    try { $("#newOrg").show(); } catch ( e ) {}
    try { $("#initForm").show(); } catch ( e ) {}
    try { $("#btnNew").removeClass("btn-success"); } catch ( e ) {}
    try { __newOrgMode = false; } catch ( e ) {}
    
    
}

/////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    이용 예제
//
/////////////////////////////////////////////////////////////////////////////////////////////////////
function sample_init_tree(){ // Factory
	var idx = 0;
    var tree = new Tree( "ROOT", "[ROOT]", "00", "00", "000", idx++, 'N' );
    // Level-1 node list
    tree.add( new Tree( "01", "메뉴1", "01", "", "", idx++, 'N' ) );
    tree.add( new Tree( "02", "메뉴2", "02", "", "", idx++, 'N' ) );
    tree.add( new Tree( "03", "메뉴3", "03", "", "", idx++, 'N' ) );
    tree.add( new Tree( "04", "메뉴4", "04", "", "", idx++, 'N' ) );
    tree.add( new Tree( "05", "메뉴5", "05", "", "", idx++, 'N' ) );
    tree.visible = true;
    
    // level-2 node list in the '03'
    var menu3 = tree.nodes["03"];
    menu3.add( new Tree( "0301", "속메뉴 1", "03", "01", "", idx++, 'N' ) );
    menu3.add( new Tree( "0302", "속메뉴 2", "03", "02", "", idx++, 'N' ) );
    menu3.add( new Tree( "0303", "속메뉴 3", "03", "03", "", idx++, 'N' ) );
    menu3.add( new Tree( "0304", "속메뉴 4", "03", "04", "", idx++, 'N' ) );
    menu3.add( new Tree( "0305", "속메뉴 5", "03", "05", "", idx++, 'N' ) );
    menu3.visible = true;
    
    // level-3 node list in the '0304' on the level-2 '0304001'
    //var menu3_4 = new Tree( "0304", "하위에 더 추가", "03","04","" );
    //menu3_4.add( new Tree( "0304001", "Google" , "03","04","01") );
    //menu3.add( menu3_4 );
    var menu3_4 = menu3.nodes["0304"];
    menu3_4.add( new Tree( "0304001", "Google" , "03","04","01", idx++, 'N' ) );
    menu3_4.add( new Tree( "0304002", "Naver"  , "03","04","02", idx++, 'N' ) );
    menu3_4.add( new Tree( "0304003", "<a href='http://php.net' target='_blank'>PHP</a>" , "03","04","03", idx++, 'N' ) );
    
    return tree;
}
//var SAMPLE_TREE = sample_init_tree();
//SAMPLE_TREE.print();
