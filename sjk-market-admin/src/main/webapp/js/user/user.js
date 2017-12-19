//注册验证
function RegVerEve() {
	var $btn = $("#btn");
	var $user = $("#user");
	var $pwd = $("#pwd");
	var $pwd2 = $("#pwd2");
	var $mail = $("#mail");
	var $actualName = $("#actualName");
	var $user_msg = $("#user_msg");
	var $pwd_msg = $("#pwd_msg");
	var $pwd2_msg = $("#pwd2_msg");
	var $mail_msg = $("#mail_msg");
	var $actualName_msg = $("#actualName_msg");

	$btn.click(function(e, index) {
		var user = $user.val();
		var pwd = $pwd.val();
		var pwd2 = $pwd2.val();
		var mail = $mail.val();
		var actualName = $actualName.val();

		if (user_va(user) && pwd_va(pwd) && pwd_va(pwd2) && pwd2_va(pwd, pwd2)
				&& mail_va(mail) && name_va(actualName)) {
			$.ajax({
				type : "POST",
				url : "../user/userReg.d",
				data : {
					name : $user.val(),
					password : $pwd.val(),
					email : $mail.val(),
					actualName : $actualName.val()
				},
				success : function(data) {
					if (data.status) {
						window.location.href = "../fin.html";
					} else {
						$user_msg.text("用户名或者邮箱被注册过。")
					}

				}
			})
		} else {
			if (!user_va(user)) {
				$user_msg.text("请输入正确的帐号")
			}
			if (!pwd_va(pwd)) {
				$pwd_msg.text("请输入正确的密码")
			}
			if (!pwd_va(pwd2)) {
				$pwd2_msg.text("请输入正确的重复密码")
			}
			if (!pwd2_va(pwd, pwd2)) {
				$pwd2_msg.text("请输入正确的重复密码")
			}
			if (!mail_va(mail)) {
				$mail_msg.text("请输入正确的邮箱")
			}
			if (!name_va(actualName)) {
				$actualName_msg.text("请输入正确的真实姓名")
			}
		}
	})
	$(document).mouseup(function(e) {
		$user_msg.text("");
		$pwd_msg.text("");
		$pwd2_msg.text("");
		$mail_msg.text("");
		$actualName_msg.text("");
	});
}

// 找回密码验证
function BaPassEve() {
	var $b_btn = $("#b_btn");
	var $user = $("#user");
	var $mail = $("#mail");
	var $user_msg = $("#user_msg");
	var $mail_msg = $("#mail_msg");

	$b_btn.click(function(e, index) {
		var user = $user.val();
		var mail = $mail.val();
		if (user_va(user) && mail_va(mail)) {
			$.ajax({
				type : "POST",
				url : "",
				data : {
					user : $user.val(),
					mail : $mail.val(),
					submit : 'submit'
				},
				success : function() {
					location.href = "fin.html";
				}
			});
		} else {
			if (!user_va(user)) {
				$user_msg.text("请输入正确的帐号")
			}
			if (!mail_va(mail)) {
				$mail_msg.text("请输入正确的邮箱")
			}
		}
	})
	$(document).mouseup(function(e) {
		$user_msg.text("");
		$mail_msg.text("");
	});
}

// 后台普通用户修改资料验证
function EditDaEve() {
	var $ed_btn = $("#ed_btn");
	var $name = $("#name");
	var $actualName = $("#actualName");
	var $email = $("#email");

	var $actualName_msg = $("#actualName_msg");
	var $email_msg = $("#email_msg");

	$ed_btn.click(function(e, index) {
		var actualName = $actualName.val();
		var mail = $email.val();
		if (name_va(name) && mail_va(mail)) {
			$.ajax({
				type : "POST",
				dataType : 'json',
				url : "../user/edit.d",
				data : {
					id : $("#user_id").val(),
					name : $name.text(),
					password : $("#pwd").val(),
					actualName : actualName,
					email : $email.val(),
				},
				success : function(data) {
					if (data.status) {
						$("#msg").text("保存成功。");
					}
					window.reload();
				}
			});
		} else {
			if (!name_va(actualName)) {
				$actualName_msg.text("请输入正确的真实名称")
			}
			if (!mail_va(mail)) {
				$email_msg.text("请输入正确的邮箱")
			}

		}
	})
	$(document).mouseup(function(e) {
		$actualName_msg.text("");
		$email_msg.text("");
		$("#msg").text("");
	});
}

// 后台普通用户修改密码验证
function EdPwdEve() {
	var $edpwd_btn = $("#edpwd_btn");
	var $o_pwd = $("#o_pwd");
	var $n_pwd = $("#n_pwd");
	var $n_pwd2 = $("#n_pwd2");
	var $opwd_msg = $("#opwd_msg");
	var $npwd_msg = $("#npwd_msg");
	var $npwd2_msg = $("#npwd2_msg");

	$edpwd_btn
			.click(function(e, index) {
				var o_pwd = $o_pwd.val();
				var n_pwd = $n_pwd.val();
				var n_pwd2 = $n_pwd2.val();
				if (pwd_va(o_pwd) && pwd_va(n_pwd) && pwd_va(n_pwd2)
						&& pwd2_va(n_pwd, n_pwd2)) {
					$
							.ajax({
								type : "POST",
								url : "../user/updatePWD.d",
								dataType : 'json',
								contentType : "application/x-www-form-urlencoded; charset=utf-8",
								data : {
									o_pwd : $o_pwd.val(),
									n_pwd : $n_pwd.val()
								},
								success : function(data) {
									if (data.status) {
										$("#msg").text("保存成功。");
									} else {
										$("#msg").text(data.msg);
									}

								}
							});
				} else {
					if (!pwd_va(o_pwd)) {
						$opwd_msg.text("请输入正确的旧密码")
					}
					if (!pwd_va(n_pwd)) {
						$npwd_msg.text("请输入正确的新密码")
					}
					if (!pwd_va(n_pwd2)) {
						$npwd2_msg.text("请输入正确的重复密码")
					}
					if (!pwd2_va(n_pwd, n_pwd2)) {
						$npwd2_msg.text("请输入正确的重复密码")
					}
				}
			})
	$(document).mouseup(function(e) {
		$opwd_msg.text("");
		$npwd_msg.text("");
		$npwd2_msg.text("");
		$("#msg").text("");
	});
}

// 后台添加新用户验证
function AddUserEve() {
	var $adedpwd_btn = $("#adedpwd_btn");
	var $user = $("#user");// 用户名
	var $name = $("#name");// 真实姓名
	var $pwd = $("#pwd");// 密码
	var $pwd2 = $("#pwd2");
	var $mail = $("#mail");// 邮箱

	var $user_msg = $("#user_msg");
	var $name_msg = $("#name_msg");
	var $pwd_msg = $("#pwd_msg");
	var $pwd2_msg = $("#pwd2_msg");
	var $mail_msg = $("#mail_msg");

	$adedpwd_btn.click(function(e, index) {
		var user = $user.val();
		var name = $name.val();
		var pwd = $pwd.val();
		var pwd2 = $pwd2.val();
		var mail = $mail.val();
		if (user_va(user) && name_va(name) && pwd_va(pwd) && pwd_va(pwd2)
				&& pwd2_va(pwd, pwd2) && mail_va(mail)) {
			$.ajax({
				type : "POST",
				url : "../user/userReg.d",
				data : {
					name : $user.val(),
					actualName : $name.val(),
					password : $pwd.val(),
					email : $mail.val(),
				},
				success : function() {
					location.href = "../user/userlist.html";
				}
			});
		} else {
			if (!user_va(user)) {
				$user_msg.text("请输入正确的帐号")
			}
			if (!name_va(name)) {
				$name_msg.text("请输入正确的姓名")
			}
			if (!pwd_va(pwd)) {
				$pwd_msg.text("请输入正确的密码")
			}
			if (!pwd_va(pwd2)) {
				$pwd2_msg.text("请输入正确的重复密码")
			}
			if (!pwd2_va(pwd, pwd2)) {
				$pwd2_msg.text("请输入正确的重复密码")
			}
			if (!mail_va(mail)) {
				$mail_msg.text("请输入正确的邮箱")
			}
		}
	})
	$(document).mouseup(function(e) {
		$user_msg.text("");
		$pwd_msg.text("");
		$pwd2_msg.text("");
		$name_msg.text("");
		$mail_msg.text("");
	});
}

// 后台管理员修改资料验证
function AdEditDaEve() {
	var $aded_btn = $("#aded_btn");
	var $name = $("#name");
	// 用户名,帐号
	var $actualName = $("#actualName");
	// 真实姓名
	var $email = $("#email");
	var $mobile = $("#mobile");
	var $workTel = $("#workTel");

	var $name_msg = $("#name_msg");
	var $actualName_msg = $("#actualName_msg");
	var $email_msg = $("#email_msg");

	$aded_btn.click(function(e, index) {
		var name = $name.val();
		var actualName = $actualName.val();
		var email = $email.val();

		if (user_va(name) && name_va(actualName) && mail_va(email)) {
			$.ajax({
				type : "POST",
				url : "../user/edit.d",
				data : {
					id : $("#userId").val(),
					name : name,
					actualName : actualName,
					email : email,
					password : $("#password").val()
				},
				success : function() {
					location.href = "./userlist.html";
				}
			});
		} else {
			if (!user_va(name)) {
				$name_msg.text("请输入正确的帐号")
			}
			if (!name_va(actualName)) {
				$actualName.text("请输入正确的姓名")
			}
			if (!mail_va(email)) {
				$email_msg.text("请输入正确的邮箱")
			}
		}
	})
	$(document).mouseup(function(e) {
		$name_msg.text("");
		$actualName.text("");
		$email_msg.text("");
	});
}

// 后台管理员修改密码验证
function AdEdPwdEve() {
	var $adedpwd_btn = $("#adedpwd_btn");
	var $n_pwd = $("#n_pwd");
	var $n_pwd2 = $("#n_pwd2");
	var $npwd_msg = $("#npwd_msg");
	var $npwd2_msg = $("#npwd2_msg");

	$adedpwd_btn.click(function(e, index) {
		var n_pwd = $n_pwd.val();
		var n_pwd2 = $n_pwd2.val();
		if (pwd_va(n_pwd) && pwd_va(n_pwd2) && pwd2_va(n_pwd, n_pwd2)) {
			$.ajax({
				type : "POST",
				url : "../user/updatePWDByAdmin.d",
				dataType : 'json',
				data : {
					password : $n_pwd.val(),
					userId : $("#user_id").val()
				},
				success : function(data) {
					// alert(data);
					// if(!data.status) {
					$("#msg").text(data.msg);
					// }
				}
			});
		} else {
			if (!pwd_va(n_pwd)) {
				$npwd_msg.text("请输入正确的新密码")
			}
			if (!pwd_va(n_pwd2)) {
				$npwd2_msg.text("请输入正确的重复密码")
			}
			if (!pwd2_va(n_pwd, n_pwd2)) {
				$npwd2_msg.text("请输入正确的重复密码")
			}
		}
	})
	$(document).mouseup(function(e) {
		$npwd_msg.text("");
		$npwd2_msg.text("");
	});
}