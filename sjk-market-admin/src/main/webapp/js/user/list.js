var urlWithParam = "/sjk-market-admin";
var user = {
		init:function(){
			this
			.getInfo()
			.loadGridTable()
			;
		},
		//取用户信息
		getInfo:function(){

			var that = this;
			$.ajax({
				type:"POST",
				url: urlWithParam + "/admin/user/get-curuser.d",
				success:function(data){
					if(null != data.result && '' != data.result && 'null' != data.result){
						that.info = data.result;
						that.bindEve();
					}
					else{
						that.goHome();				
					}
				},
				error:function(){
					that.goHome();
				},
				complete:function(){
					return that;
				}
				
			});
			return that;
		},
		//修改信息
		edit:function(id,name,password,email,actualName,mobile,workTel,userType){

			var that = this;
			var data = that.info;
			var currUser = false, supUser = false;
			if(id==data.id){currUser = true;}
			if(data.userType==1){ supUser = true;}	
			if(!currUser && !supUser){
					that.showMessager('<span class="co_red">没有权限</span>');
					return false;			
				}
			
			var $addUser = $("#addUser"), $addPost = $("#addPost"), $closePost = $("#closePost");
			var url = '/sjk-market-admin/admin/user/save.d',
				id = id || 0,
				$id = $addUser.find('input[name="id"]'),
				$name = $addUser.find('input[name="name"]'),
				$password = $addUser.find('input[name="password"]'),
				$repassword = $addUser.find('input[name="repassword"]'),
				$email = $addUser.find('input[name="email"]'),
				$actualName = $addUser.find('input[name="actualName"]'),
				$mobile = $addUser.find('input[name="mobile"]'),
				$workTel = $addUser.find('input[name="workTel"]'),
				$userType = $addUser.find('input[name="userType"]');

			if(id>0){
				url = '/sjk-market-admin/admin/user/edit.d';
				$id.val(id);
				$name.val(name);
				$password.val(password).parent().parent().hide();		
				$repassword.val(password).parent().parent().hide();
				$email.val(email);
				$actualName.val(actualName);
				$mobile.val(mobile);
				$workTel.val(workTel);
				$addUser.find('input[name="userType"][value="'+ userType +'"]:radio').attr('checked','checked');
				
			}
			else{
				$id.val(0);
				$name.val('');
				$password.val('').parent().parent().show();
				$repassword.val('').parent().parent().show();
				$email.val('');
				$actualName.val('');
				$mobile.val('');
				$workTel.val('');
				
			}
			if(!supUser){
				$userType.parent().parent().hide();
			}else{
				$userType.parent().parent().show();			
			}
			
			$addUser.window({
				title : (id>0) ? '修改用户' : '新增用户',
				top : '100px',
				width : 600,
				modal : true,
				shadow : false,
				closed : false,
				height : 500
			});
			
			$addUser.show();
			$addPost.unbind('click').bind({
				
				click : function() {
					
					if(!that.isNull($name,'登录名称')) return false; 
					if(!that.isNull($password,'密码')) return false; 
					if(!that.isNull($repassword,'重复密码')) return false; 
					if(!that.isRep($password,$repassword)) return false;
					
					var idd = $id.val(),
						name = $name.val();
						password =  $password.val();
						email = $email.val();
						actualName = $actualName.val();
						mobile = $mobile.val();
						workTel =  $workTel.val();
						userType =  $addUser.find('input[name="userType"][checked]:radio').val();
						
					$.ajax({
						type:"POST",
						url:url,
						data:{
								id:idd,
								name:name,
								password:password,
								email:email,
								actualName:actualName,
								mobile:mobile,
								workTel:workTel,
								userType:userType
						},
						error:function(){
							that.showMessager('<span class="co_red">操作失败</span>');					
						},
						success:function(data){										
							if (data != null) {
								that.showMessager('保存成功');
								$addUser.window('close');
								
								$('#dataList').datagrid('reload');
							}
						}
						
					})
				}
			})

			$closePost.bind({
				click : function() {
					$addUser.window('close');
				}
			})

			

			
		},
		//删除
		del:function(id){

			if(!id) return false;
			var that = this;	
			var data = that.info;
			if(id==data.id){
				that.showMessager('<span class="co_red">不能删除自己</span>');
				return false;				
			}	
			if(data.userType!=1){
					that.showMessager('<span class="co_red">没有权限</span>');
					return false;			
			}

			$.messager.confirm('确认', '您确认要删除这条记录吗?', function(row) {
				
				if (row) {
					$.ajax({
						type : "POST",
						url : urlWithParam + "/admin/user/del.d", 
						data : {
							id: id
						},
						dataType : 'json',
						success : function(data) {
							if (data.result.msg == "OK!") {
								
								that.showMessager("删除操作成功");
								$('#dataList').datagrid('reload');
							} else {
								that.showMessager(data.result.msg);
							}
						},
					error:function(){
						that.showMessager('<span class="co_red">删除操作失败</span>');	
						
					}
					});
				} else {
					return false;
				}
			});

			
		},
		
		//加载表
		loadGridTable:function(){
			var that = this;
			$('#dataList')
					.datagrid(
							{
								loadMsg : '正在加载...',
								iconCls : 'icon-save',
								width : '1040',
								nowrap : false,
								striped : true,
								collapsible : false,
								url : urlWithParam + "/admin/user/search.json",
								pageSize : 20,
								sortOrder : 'desc',
								idField : "id",
								columns : [ [
										
										{
											field : 'id',
											title : 'id',
											sortable : true,
											width : 50
										},
										{
											field : 'name',
											title : '登录名称',
											sortable : true,
											width : 80
										},
										{
											field : 'actualName',
											title : '真实姓名',
											sortable : true,
											width : 80
										},
										{
											field : 'userType',
											title : '类型',
											sortable : true,
											width : 50,
											formatter : function(value, rec, index) {
												if (value == 0) {
													return "用户";
												} else if (value == 1) {
													return "管理员";
												}
											}
										},
										{
											field : 'mobile',
											title : '手机',
											sortable : true,
											width : 60
										},
										{
											field : 'workTel',
											title : '座机',
											sortable : true,
											width : 60
										},
										{
											field : 'email',
											title : '电子邮件',
											sortable : true,
											width : 200
										},
										{
											field : 'operate',
											title : '操作',
											width : 140,
											formatter : function(value, rec, index) {
												
												var del = '<a class="mr_15 co_bl f_l" onclick="user.del('
														+ rec.id + ')">删除</a>';
												var edit = '<a class="mr_10 co_bl f_l" onclick="user.edit('
														+ rec.id
														+ ',\''
														+ rec.name
														+ '\',\''
														+ rec.password
														+ '\',\''
														+ rec.email
														+ '\',\''
														+ rec.actualName
														+ '\',\''
														+ rec.mobile
														+ '\',\''
														+ rec.workTel
														+ '\',\''
														+ rec.userType
														+ '\');">修改</a>';
												
												var ps = '<a class="mr_15 co_bl f_l" onclick="user.editPs('
													+ rec.id
													+ ',\''
													+ rec.name
													+ '\')">修改密码</a>';
												return edit + del + ps;
											}
										} ] ],
								pagination : true,
								rownumbers : false,
				onLoadError : function() {
		            var params = arguments;
		            if(params[0].status == 403) {
		                that.goHome();
		            }
		        },
				onLoadSuccess : function() {
									that.setBtnDisabled("search", false);
								}
							});


			return that;
		},
		//修改密码
		editPs:function(id,loginName){
			var that = this;
			if(!id || id==0) return false;
			var url = "/admin/user/update-pwd.d";
			var data = that.info;
			var currUser = false, supUser = false;
			if(id==data.id){currUser = true;}
			if(data.userType==1){ supUser = true;}	
			if(!currUser && !supUser){
					that.showMessager('<span class="co_red">没有权限</span>');
					return false;			
				}			
			if(supUser && !currUser){url = "/admin/user/update-pwd-admin.d";}
			
			var $editPs = $('#editPs'),
				$loginName = $('#loginName'),
				$id = $editPs.find('input[name="id"]'),
				$o_pwd = $editPs.find('input[name="o_pwd"]'), 
				$password = $editPs.find('input[name="password"]'),
				$repassword = $editPs.find('input[name="repassword"]'),
				$psPost = $('#psPost'),
				$psClose = $('#psClose');
			
				if(!currUser){
					$o_pwd.parent().parent().hide();
				}else{
					$o_pwd.parent().parent().show();		
				}
			
			$id.val(id);
			$loginName.text(loginName||'');
			$o_pwd.val('');
			$password.val('');
			$repassword.val('');
			$editPs.window({
				title : '修改密码',
				top : '100px',
				width : 600,
				modal : true,
				shadow : false,
				closed : false,
				height : 500
			});	
			$editPs.show();
			$psPost.unbind('click').bind({
				click : function() {
					if(currUser){
						if(!that.isNull($o_pwd,'旧密码')) return false; 
					}
					if(!that.isNull($password,'新密码')) return false; 
					if(!that.isNull($repassword,'重复密码')) return false; 
					if(!that.isRep($password,$repassword)) return false;
					
					var idd = $id.val(),
						password =  $password.val(),
						oldps = $o_pwd.val();

					$.ajax({
						type:"POST",
						url:urlWithParam + url,
						data:{
								id:idd,
								o_pwd:oldps,
								n_pwd:password,
								password:password
						},
						error:function(){
							that.showMessager('<span class="co_red">操作失败</span>');					
						},
						success:function(data){										
							if (data.status) {
								if(currUser){
									alert('修改密码成功，请重新登录');
									 window.parent.location.href = "../login.html";	
								}
								that.showMessager(data.msg);
								$editPs.window('close');
							}else
							{
								that.showMessager(data.msg);
							}
							
						}
						
					})
				}
			})

			$psClose.bind({
				click : function() {
					$editPs.window('close');
				}
			})		
			
			},
		//搜索
		search:function(){	
			var that = this;
			that.setBtnDisabled("true", false);
		    var queryParams = $('#dataList').datagrid('options').queryParams;
		    var keyword = $.trim($("#keyword").val());    
		    
		    queryParams.keywords = (keyword !== "" ? keyword : '');   
		    
		    queryParams.page = 1;

		    // 主显示使用
		    $('#dataList').datagrid('options').pageNumber = 1;
		    // page对象 中的pageNumber对象
		    var pager = $('#dataList').datagrid('getPager');
		    $(pager).pagination('options').pageNumber = 1;
		    $('#dataList').datagrid('reload');

		},
		//事件绑定
		bindEve:function(){
			var that = this;
			var d =that.info;
			$('#myBtn').bind({
		        click : function() {
		        	that.edit(d.id,d.name,d.password,d.email,d.actualName,d.mobile,d.workTel,d.userType);
		        }
			});
		
			$('#mypsBtn').bind({
		        click : function() {
		            that.editPs(d.id,d.name);
		        }
			});
			
			$('#addBtn').bind({
				click:function() {	
					that.edit();
				}
			});
			$('#search').bind({
		        click : function() {
		            that.search();
		        }
			});
			return that;
		},
		//回登录页
		goHome:function(){
			alert('请先登录');
            window.parent.location.href = "../login.html";	
			
		},
		//设置搜索按钮可用状态
		setBtnDisabled:function(btnId, disabled) {
			if (disabled) {
				$("#" + btnId).attr("style", "color:#999").attr("disabled", "disabled");
			} else {
				$("#" + btnId).removeAttr("style").removeAttr("disabled");
			}
		},
		//密码是否相同
		isRep:function(obj1,obj2){
			var that = this;
			if(obj1.val()!=obj2.val()){
				that.showMessager('<span class="co_red">两次密码不一致</span>');	
				return false;
			}
			return true
			
		},
		//是否为空值
		isNull:function(obj,tit){
			var that = this;
			if(obj.val().length<1){
				that.showMessager('<span class="co_red">'+tit+'不能为空</span>');	
				return false;
			}
			return true
			
		},
		//操作提示
		showMessager:function(msg) {
			if (msg == null || msg == "" || msg == undefined)
				msg = "操作成功";
			$.messager.show({
				msg : msg,
				title : '提示',
				showType : 'slide',
				timeout : 3000
			});
		}
		
}

$(document).ready(function() {
	user.init();
	
});



