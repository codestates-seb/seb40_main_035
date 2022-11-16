import ArticleCard from '../components/ArticleCard';

const dummy = [
  {
    articleId: 1,
    title:
      '헬스케어 식단 관련 프로젝트를 같이 진행할 백엔드 개발자님을 구합니다',
    body: '본문1',
    views: 23,
    isCompleted: false,
    startDay: '20221109',
    endDay: '20221210',
    backend: 3,
    frontend: 3,
    memberId: 1,
    memberName: '홍길동',
    createdAt: '2022-11-15T17:21:00.0288716',
    modifiedAt: '2022-11-15T17:21:00.0288716',
    heartCount: 10,
    answerCount: 1,
    hashtags: [
      {
        hashtagId: 1,
        name: '해쉬태그1',
      },
    ],
    interests: [
      {
        interestId: 1,
        name: '교육',
      },
    ],
    skills: [
      {
        skillId: 1,
        name: 'JavaScript',
      },
      {
        skillId: 3,
        name: 'React',
      },
    ],
  },
  {
    articleId: 2,
    title: '제목2',
    body: '본문2',
    views: 0,
    isCompleted: false,
    startDay: '20221110',
    endDay: '20221211',
    backend: 2,
    frontend: 2,
    memberId: 2,
    memberName: '고길동',
    createdAt: '2022-11-15T17:21:00.0288716',
    modifiedAt: '2022-11-15T17:21:00.0288716',
    heartCount: 0,
    answerCount: 1,
    hashtags: [
      {
        hashtagId: 1,
        name: '해쉬태그1',
      },
    ],
    interests: [
      {
        interestId: 1,
        name: '미디어',
      },
    ],
    skills: [
      {
        skillId: 1,
        name: 'spring',
      },
    ],
  },
  {
    articleId: 3,
    title: '제목3',
    body: '본문3',
    views: 0,
    isCompleted: true,
    startDay: '20221111',
    endDay: '20221212',
    backend: 3,
    frontend: 4,
    memberId: 3,
    memberName: '김길동',
    createdAt: '2022-11-15T17:21:00.0288716',
    modifiedAt: '2022-11-15T17:21:00.0288716',
    heartCount: 0,
    answerCount: 1,
    hashtags: [
      {
        hashtagId: 1,
        name: '해쉬태그1',
      },
    ],
    interests: [
      {
        interestId: 1,
        name: '제조',
      },
    ],
    skills: [
      {
        skillId: 1,
        name: 'Nodejs',
      },
    ],
  },
];

const Main = () => {
  return (
    // 사용시 dummy를 아티클 목록이 담긴 배열로 교체해주세요!:)
    <div>
      {dummy.map((article) => {
        return (
          <ArticleCard
            key={article.articleId}
            articleId={article.articleId}
            isCompleted={article.isCompleted}
            title={article.title}
            startDay={article.startDay}
            endDay={article.endDay}
            frontend={article.frontend}
            backend={article.backend}
            hashtags={article.hashtags}
            skills={article.skills}
            memberName={article.memberName}
            heartCount={article.heartCount}
            views={article.views}
          />
        );
      })}
    </div>
  );
};

export default Main;
