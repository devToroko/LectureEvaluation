# LectureEvaluation
JSP/Servlet을 이용한 간단한 강의 평가 사이트입니다.

사용한 도구:
1) Eclipse EE
2) MySQL

실행을 하기 앞서서 준비할 것이 있습니다.

1) lectureevaluation 라는 database를 생성하고 안에

CREATE TABLE `evaluation` (
  `evaluationID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` varchar(20) DEFAULT NULL,
  `lectureName` varchar(50) DEFAULT NULL,
  `professorName` varchar(20) DEFAULT NULL,
  `lectureYear` int(11) DEFAULT NULL,
  `semesterDivide` varchar(20) DEFAULT NULL,
  `lectureDivide` varchar(10) DEFAULT NULL,
  `evaluationTitle` varchar(50) DEFAULT NULL,
  `evaluationContent` varchar(2048) DEFAULT NULL,
  `totalScore` varchar(5) DEFAULT NULL,
  `creditScore` varchar(5) DEFAULT NULL,
  `comfortableScore` varchar(5) DEFAULT NULL,
  `lectureScore` varchar(5) DEFAULT NULL,
  `likeCount` int(11) DEFAULT NULL,
  PRIMARY KEY (`evaluationID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8

CREATE TABLE `likey` (
  `userID` varchar(20) NOT NULL,
  `evaluationID` int(11) NOT NULL,
  `userIP` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`userID`,`evaluationID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `user` (
  `userID` varchar(20) NOT NULL,
  `userPassword` varchar(64) DEFAULT NULL,
  `userEmail` varchar(50) DEFAULT NULL,
  `userEmailHash` varchar(64) DEFAULT NULL,
  `userEmailChecked` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`userID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

를 생성하시고 web.xml 에 가시면 context-param에 PoolConfig라는 부분이 있습니다. 내부의 param-value에서 dbUser 와 dbPass 를 본인의 것에 맞게 수정하십쇼

2) util.Gmail 이라는 클래스가 있습니다. 내부에 본인의 구글 아이디와 구글 비번을 입력하시면 됩니다.

public class Gmail extends Authenticator {

	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		//1번째 파라미터에는 본인의 구글 아이디, 2번째 파라미터로는 해당 아이디의 비밀번호를 입력하세요! 
		return new PasswordAuthentication("본인구글아이디", "비번");//관리자 자신의 지메일
	}
	
}


그리고 실행하면 됩니다.

