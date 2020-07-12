/**
 * javascript 에서 java의 Map처럼 동일하게 사용하기 위한 lib.
 * 
 * Map사용법) 
   var map= new JavaMap();
   map.put("id", "atspeed");
   map.put("name", "benjamin");
   map.jsonString(); //json serialized string 
   map.get("id");
   
 * Map 송수신) 
   전송 : map.jsonString(), 
   수신 : JSON.parse(데이터)
   
 */


JavaMap = function(){
	this.map = new Object();
};

JavaMap.prototype = { 
	
	put : function(key, value){   
		this.map[key] = value;
	}
	
	, putMap : function(key, value){
		this.map[key] = value.map;
	}
	
	, putMapList : function(key, value){
		var list = new Array();
		for(var i=0;i<value.length;i++){
			list.push(value[i].map);
		}
		this.map[key] = list;
	}
	
	, get : function(key){   
		return this.map[key];
	}
	
	, containsKey : function(key){    
		return key in this.map;
	}
	
	, containsValue : function(value){    
		for(var prop in this.map){
			if(this.map[prop] == value) return true;
		}
		return false;
	}
	
	, isEmpty : function(key){    
		return (this.size() == 0);
	}
	
	, clear : function(){   
		for(var prop in this.map){
			delete this.map[prop];
		}
	}
	
	, remove : function(key){    
		delete this.map[key];
	}
	
	, keys : function(){
		var keys = new Array();
		for(var prop in this.map){
			keys.push(prop);
		}
		return keys;
	}
	
	, values : function(){   
		var values = new Array();   
		for(var prop in this.map){   
			values.push(this.map[prop]);
		}   
		return values;
	}
	
	, size : function() {
		var count = 0;
		for (var prop in this.map) {
			count++;
		}
		return count;
	}
	
	, jsonString: function(){
		return JSON.stringify(this.map);    
	}
};