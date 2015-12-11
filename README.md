# Together  
  
### 1. 문제정의
---
* **문제상황 시나리오**
    >대학교에 갓 복학한 20대 대학생, 남궁호구씨. 지난 학기 팀프로젝트에서 지뢰를 밞았다. 자신이 자료준비부터 발표까지 모든 것을 혼자서 하게 되었다. 이번 학기에도 팀프로젝트가 있는 수업을 듣게 되었는데, 아는 친구도 없고, 누가 열심히 하는지 누가 지뢰인지 알 수가 없어서 답답하다. 이번 학기에는 버스드라이버가 되고 싶지 않다.

* **문제정의하기**  
팀 프로젝트 진행 시, 팀원의 참여도를 어떻게 향상시킬 수 있을까?  
→ 어떻게 팀을 구성해도 쳐지는 팀원은 생기기 마련이다.  
→ 모든 팀원들이 모두 참여하는 효율적인 팀 운영이 어렵다.  
Why? 다른 팀원들이 서로의 능력과 무엇을 하고 있는지 파악하기 어렵다.

* **진짜 문제 정의**  
모든 팀원들이 팀 프로젝트에서 각자 역할 수행을 잘하고 있는지, 무엇을 잘하는지 파악하기 어렵다.

### 2. 필요성
---
한국기술교육대학교뿐만 아니라 전국 많은 대학생들이 팀 프로젝트를 수행한다. 알바몬에서 조사한 자료에 따르면, 캠퍼스생활 최악의 꼴불견 TOP10에서 19.9% 득표율로 1등을 차지한 것이 ‘조별 과제(팀 프로젝트) 수고 없이 묻어가는 조원’이다.  

![캠퍼스꼴불견](http://cfile8.uf.tistory.com/image/184AA94C50BAB5F22C6B0C)

인기있는 코미디 프로그램인 ‘SNL 코리아’에서도 ‘조별과제 잔혹사’라는 이름 두 번이나 조별과제(팀 프로젝트)를 패러디하면서 대학생들의 큰 공감을 불러 일으켰다. 따라서 묻어가는 조원들 없이, 모두 열심히 팀 프로젝트에 참여할 수 있는 시스템이 요구된다. 

![SNL](http://www.womennews.co.kr/data/news/1241/201306021716202UI.jpg)

### 3. 요구사항
---
- 팀원들의 역할을 서로 파악할 수 있어야 한다.
- 팀원들에게 주어진 과제의 진행사항을 서로 공유할 수 있어야 한다.
- 서로의 과제진행 대해서 피드백이 가능해야 한다.
- 팀 프로젝트 종료 시, 각자 팀원들의 과제수행율과 참여도를 수치화 한다.
- 수치화된 개인의 과제수행능력을 다른 유저들이 참고할 수 있다.
- 수치화된 데이터가 팀원구성에 참고될 수 있다.
- 이미 종료된 각 프로젝트의 진행내역과 결과가 저장되어 추후에 열람이 가능하다.

### 4. 개발환경
---
* 안드로이드 스튜디오로 작업가능한 개발용 컴퓨터
    * Microsoft® Windows® 8/7/Vista (32 or 64-bit)  
    * 2 GB RAM minimum, 4 GB RAM recommended  
    * 400 MB hard disk space  
    * Java Development Kit (JDK) 7
* 테스트용 스마트폰(최소사양)
    * CPU: 1.5Ghz 듀얼코어
    * OS: Android Kitkat 4.4.2
    * RAM : 1GB
* 서버
    * 하드웨어
        * DB서버 1대
        * web 1대
    * 플랫폼 : Django Web Framework
    * DB : PostgreSQL

### 5. 필요기술
---
- Android Studio를 이용한 어플리케이션 개발기술
- GCM(Google cloud Messaging)
- REST framework
- Django Web Framework
- PostgreSQL

### 6. 데이터베이스
---
* USER
![USER](http://dl.dropbox.com/s/rj7rcmknllwm0l6/1.png)

* PROJECT
![PROJECT](http://dl.dropbox.com/s/oq28ruwhvs7y209/2.png)

* PROJECT_INFO
![PROJECT_INFO](http://dl.dropbox.com/s/heo6ks4tlv2dzdb/3.png)

* TASK
![TASK](http://dl.dropbox.com/s/pd60k0jxa8uuxx9/4.png)

* Comment
![Comment](http://dl.dropbox.com/s/7a38913jqrgzfzg/5.png)
