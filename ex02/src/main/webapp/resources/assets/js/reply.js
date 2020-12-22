/**
 *  Javascript의 모듈화
 *  
 *  함수들을 하나의 모듈처럼 묶음으로 구성하는 것을 의미한다
 *  화면 내에서 javascript처리흫 하다보면 이벤트 처리와 DOM, Ajax처리 등이
 *  복잡하게 섞여서 유지보수가 어렵다. 따라서 javascript를 하나의 모듈처럼 수성하여 사용한다.
 *  
 *  a(data){
 *  	결과 : 메소드
 *  	callback(data)
 *  }
 *   
 *  reply modules
 */

console.log("Reply Modules............");

var replyService = (function() {
	
	//댓글 추가
	//callback, error는 와부이서 전달받은 함수이다.
	//함수의 파라미터 개수를 일치시킬 필요가 없기 떄문에
	//사용시 callback이나 error와 같은 파라미터는 상황에 따라 작성한다
	function add(reply, callback, error) {
		console.log("add reply.......");
		
		$.ajax({
			type:"POST",
			url:"/replies/new",
			data:JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success:function(result, status, xhr){
				if(callback){
					console.log(result);
					//외부에서 전달받은 값이 있다면 결과를 해당 함수에 전달하여 사용한다.
					callback(result);
				}
			},
			error:function(xhr, status, er){
				if(error){
					//외부에서 전달 받은 값이 있다면 er객체를 전달한다
					error(er);
				}
			}
		});
	}
	
	//댓글 목록
	function getList(param,callback,error) {
		var bno = param.bno;
		
		//var v = a || b	: a에 값이 있으면 a를 v에 할당, 없으면 b를 v에 할당
		var page = param.page || 1
		
		//$.getJSON은 통신 관련 상세 설정은 지원하지 않으나, json데이터를 화면에 뿌려줄 때
		//간결한 소스코드를 활용할 수 있게 해준다
		
		//컨트롤러에 있는 페이지 목록 조회 메소드를 json타입으로 호출한다
		$.getJSON("/replies/pages/"+bno+"/"+page+".json", 
			function(data){
			//페이지 목록 조회 컨트롤러 메서드의 리턴타입인 ReplyPageDTO를 data로 전달 받는다
				if(callback){
					//전달받은 메소드의 매개변수에 각각 댓글 전체 개수, 요청한 페이지의 게시들 List를 전달해 준다
					callback(data.replyCnt, data.list);
				}
		}).fail(function(xhr, status, er){
			if(error){
				error(er);
			}
		});
		
	}
	
	function update(reply, callback,error) {
		console.log("rno : " + reply.rno);
		console.log("reply : " + reply.reply);
		
		$.ajax({
			type:"PUT",
			url:"/replies/"+reply.rno,
			data:JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success:function(result,status, xhr){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	function remove(rno, callback, error) {
		console.log("Rno : "+ rno);
		$.ajax({
			type:"DELETE",
			url:"/replies/"+rno,
			success:function(result,status, xhr){
				if(callback){
					callback(result);
				}
			},
			error:function(xhr, status, er){
				if(error){
					error(er);
				}
			}
		});
	}
	
	//시간 처리(당일 시/분/초, 지난 날은 년/월/일)
	function displayTime(timeValue) {
		var today = new Date();
		var replyDate = new Date(timeValue);
		var gap = today.getTime() - replyDate.getTime();
		
		if(gap < 1000 * 60 * 60 * 24){
			//시/분/초
			var hh = replyDate.getHours();
			var mi = replyDate.getMinutes();
			var ss = replyDate.getSeconds();
			
			return [(hh > 9 ? '' : '0')+hh,":",(mi > 9 ? '' : '0')+mi,":",(ss > 9 ? '' : '0')+ss].join(' ');
		}else{
			var yy = replyDate.getFullYear();
			var mm = replyDate.getMonth()+1;
			var dd = replyDate.getDate();

			return [yy,"-",(mm > 9 ? '' : '0')+mm,"-",(dd > 9 ? '' : '0')+dd].join(' ');
		}
		
		
	}
	
	//return {name :"AAA"};
	return {add:add, getList:getList,update:update,remove:remove,displayTime:displayTime};
})();











