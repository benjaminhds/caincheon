<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<script type="text/javascript">
		// 댓글 저장
		function writeCmt(obj) {
			
			$.ajax({
				type	: 'POST'
				, data	: {
					i_sBlidx	 : $("#i_sBlidx", "form[name=form02]").val()
					, i_sComment : $("textarea[name=i_sComment]").val()
					, i_sPassword : obj.closest(".td_cmt").find("input[name=i_sPassword]").val()
					, mode	: "i"
				}
				, url	: "/n/board/comment_cud.do"
				, async	: false
				, dataType: "json"
				, success: function(data) {
					alert("저장되었습니다.");
					location.reload();
				},
				error: function(request, status, error) {
					alert("에러 입니다. 관리자에게 문의해주세요.");
				}
			});
		}
		// 댓글 업데이트
		function updateCmt(obj) {
			$.ajax({
				type	: 'POST'
				, data	: {
					i_sIdx	 : obj.closest(".td_cmt").find(".this_idx").val()
					, i_sComment : obj.closest(".this_area").find(".this_comment").val()
					, mode	: "u"
				}
				, url	: "/n/board/comment_cud.do"
				, async	: false
				, dataType: "json"
				, success: function(data) {
					alert("저장되었습니다.");
					location.reload();
				},
				error: function(request, status, error) {
					alert("에러 입니다. 관리자에게 문의해주세요.");
				}
			});
		}

		// 댓글 삭제
		function deleteCmt(obj) {
			if (confirm("정말 삭제하시겠습니까??") == true){
				$.ajax({
					type	: 'POST'
					, data	: {
						i_sIdx	: obj.closest(".td_cmt").find(".this_idx").val()
						, mode	: "d"
					}
					, url	: "/n/board/comment_cud.do"
					, async	: false
					, dataType: "json"
					, success: function(data) {
						alert("삭제되었습니다.");
						location.reload();
					},
					error: function(request, status, error) {
						alert("에러 입니다. 관리자에게 문의해주세요.");
					}
				});
			}
		}
		function addBtnEvent() {
			/*댓글 수정모드*/
			$(".btn_changeModi").on("click",function(){
				var userYnCommentSecret	=	"${bd_content.USEYN_COMMENT_SECRET}";
				
				/*암호 사용여부에 따른 제어*/
				if(userYnCommentSecret == 'Y') {
					confirmArea($(this));
				} else {
					changeModiMode($(this));
				}
			});
			/*확인 후 변경창으로*/
			$(".btn_confirm").on("click",function(){
				secretConfirm($(this));
			});
			/*댓글 삭제*/
			$(".btn_delete").on("click",function(){
				deleteCmt($(this));
			});
			/*댓글 저장*/
			$(".btn_update").on("click",function(){
				updateCmt($(this));
			});
			/*댓글 저장*/
			$(document).on("click",".btn_save",function(){
				writeCmt($(this));
			});
		}

		//changeMod
		function changeModiMode(obj) {
			obj.closest(".this_area").find(".this_comment_text").hide();
			obj.closest(".this_area").find(".this_comment").show();
			obj.closest("#btn").find(".area_password_confirm").hide();
			obj.closest("#btn").find(".btn_update").show();
			obj.closest("#btn").find(".btn_changeModi").hide();
			obj.closest("#btn").find(".btn_delete").hide();
		}
		
		/*컨펌 지역*/
		function confirmArea(obj) {
			obj.closest("#btn").find(".area_password_confirm").show();
			obj.hide();
			obj.closest("#btn").find(".btn_update").hide();
			obj.closest("#btn").find(".btn_delete").hide();
			
			
		}
		/*암호 확인*/
		function secretConfirm(obj) {
			var password	=	obj.closest(".area_password_confirm").find("#i_sPassword").val();
			var passwordCon	=	obj.closest(".td_cmt").find(".this_password").val();
			
			if(password == '') {
				alert("암호를 입력해주세요.");
				return false;
			}
			
			if(password == passwordCon) {
				changeModiMode(obj);
			} else {
				alert("암호가 맞지 않습니다. 다시 입력해주세요.");
				return false;
			}
		}
		</script>
				<style>
					.td_cmt {
						padding:10px 0px 0px 60px;
						vertical-align:middle;
					}
				</style>
				<div>
					<table class="table_comment" style="table-layout:fixed">
						<c:if test="${bd_content.USEYN_COMMENT eq 'Y'}">
							<c:forEach items="${comment_list}" var="list">
								<tr class="this_area">
									<!-- 아이디, 작성날짜 -->
									<td style="width:250px" class="td_cmt">
										${list.USER_ID}<br>
										<font size="2" color="blue">${list.REG_DATE}</font>
									</td>
									<!-- 본문내용 -->
									<td style="width:500px" class="td_cmt">
										<div class="text_wrapper this_comment_text">
											${list.COMMENT}
										</div>
										<textarea name="this_comment" class="this_comment" style="display:none">${list.COMMENT}</textarea>
									</td>
									<td style="width:300px" class="td_cmt">
										
										<input type="hidden" name="this_idx" class="this_idx" value="${list.IDX}">
										<input type="hidden" name="this_password" class="this_password" value="${list.COMMENT_SECRET}">
										
										<div id="btn" style="text-align:center">
											<div class="area_password_confirm" style="display:none">
												<div class="comment_secret_area">
													<span style="float:left;font-size: 15px;padding: 10px;">암호</span>
													<input type="text" style="float:left;width: 100px;height: 30px;" name="i_sPassword" id="i_sPassword">
												</div>
												<br>
												<div style="margin:30px;">
													<p><a class="btn_confirm" style="color:red" href="javascript:;" onclick="writeconfirm()">[확인]</a></p>
												</div>
											</div>
											<c:if test="${not empty SS_MEM_ID and list.USER_ID eq SS_MEM_ID}">
												<p><a class="btn_changeModi" style="color:blue"	href="javascript:;">[수정모드]</a></p>
												<p><a class="btn_delete" style="color:red"		href="javascript:;">[삭제]</a></p>
												<p><a class="btn_update" style="color:#4d4d4d;display:none"	href="#">[저장]</a></p>
											</c:if>
										</div>
									</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					
					<table style="border-top: 1px solid #ccc; margin-top:30px;">
						<!-- 로그인 된 사람만 댓글 작성 -->
						<c:if test="${not empty SS_MEM_ID}">
							<tr>
								<td width="250px" class="td_cmt">
									<div>
										${SS_MEM_ID}
									</div>
								</td>
								<td width="500px" class="td_cmt">
									<div style="overflow:auto">
										<textarea name="i_sComment" rows="4" cols="70"></textarea>
									</div>
								</td>
								<td width="300px" class="td_cmt">
									<c:if test="${bd_content.USEYN_COMMENT_SECRET eq 'Y'}">
										<div class="comment_secret_area">
											<span style="float:left;font-size: 15px;padding: 10px;">암호</span>
											<input type="text" style="float:left;width: 100px;height: 30px;" name="i_sPassword" id="i_sPassword">
										</div>
									<br>
									</c:if>
									<div style="margin:30px;">
										<p><a class="btn_save" style="color:red" href="javascript:;">[댓글 저장]</a></p>
									</div>
								</td>
							</tr>
						</c:if>
					</table>
				</div>