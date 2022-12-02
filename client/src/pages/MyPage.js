import styled from 'styled-components';
import ProfileCard from '../components/ProfileCard';
import SkillStackView from '../components/SkillStackView';
import InterestView from '../components/InterestView';
import ArticleCard from '../components/ArticleCard';
import {
  currentUserState,
  recruitedArticlesState,
  likedArticlesState,
  userProfileState,
  skillStackViewState,
  interestViewState,
} from '../atom/atom';
import { useRecoilState, useSetRecoilState, useRecoilValue } from 'recoil';
import { useState, useEffect } from 'react';
import axios from 'axios';
import getSkills from '../utils/getSkills';
import ArticlesGrid from '../components/ArticlesGrid';

const Container = styled.div`
  min-height: calc(100vh - 62px); //전체화면-헤더 높이
  width: fit-content;
  color: var(--black);

  display: flex;
  flex-direction: column;
  align-items: center;

  .profile-card {
    margin-top: 40px;
  }

  .skill-stack {
    border-radius: 8px;
    background-color: white;
    width: 762px;
    margin-top: 20px;
    padding: 20px 30px;
  }

  .interest {
    border-radius: 8px;
    background-color: white;
    width: 762px;
    margin-top: 20px;
    padding: 20px 30px;
  }

  .article-area {
    width: calc(100vw - 360px);
    min-width: 762px;
  }

  .article-menu {
    padding: 60px 0 0 0;
    display: flex;
  }

  .menu:first-child {
    border-right: 1px solid var(--purple-medium);
    margin-right: 20px;
  }

  .menu {
    background-color: transparent;
    padding: 0 20px 0 0;
    text-align: start;
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
  }

  .active-menu {
    color: var(--purple);

    .menu-content-count {
      color: var(--black);
    }
  }

  .menu-content-count {
    margin-top: 5px;
    font-size: 15px;
    font-weight: normal;
  }

  .article-list {
    /* min-height: 100px; */
    margin-bottom: 100px;
  }

  .article-list > * {
    margin: 30px 30px 0 0;
  }
`;

const MyPage = () => {
  const [activeMenu, setActiveMenu] = useState(0);

  const currentUser = useRecoilValue(currentUserState); //로그인 후, 응답으로 받아 오는 멤버아이디
  const [profileData, setProfileData] = useRecoilState(userProfileState); //프로필카드
  // const [skillStackView, setSkillStackView] =
  //   useRecoilState(skillStackViewState); //기술스택
  const setSkillStackView = useSetRecoilState(skillStackViewState); //기술스택
  const setInterestView = useSetRecoilState(interestViewState); //관심분야
  const [recruitedArticles, setRecruitedArticles] = useRecoilState(
    recruitedArticlesState,
  ); //모집 프로젝트
  const [likedArticles, setLikedArticles] = useRecoilState(likedArticlesState); //좋아요 프로젝트

  useEffect(() => {
    // let token =
    //   'Bearer eyJhbGciOiJIUzM4NCJ9.eyJuYW1lIjoi7YyM656R7J20IiwibWVtYmVySWQiOjE1LCJzdWIiOiJibHVlQGdtYWlsLmNvbSIsImlhdCI6MTY2OTYyOTQ0MCwiZXhwIjoxNjY5NjMxMjQwfQ.pAn-zeHetvAz6EkJc9NWtBSHg9F7MrkmOGtQpQNkr8qkjhwafCMvbQtPzbhSVIan';
    axios
      .get(
        `/members/${currentUser.memberId}`,
        // {
        //   headers: { Authorization: token },
        // }
      )
      .then((res) => {
        // 프로필카드 상태 set
        const userProfile = {
          memberId: res.data.data.memberId,
          email: res.data.data.email,
          name: res.data.data.name,
          description: res.data.data.description,
          level: res.data.data.level,
          github: res.data.data.github,
        };
        setProfileData(userProfile);

        setSkillStackView(getSkills(res.data.data.skills));

        //관심분야 상태 set
        setInterestView(res.data.data.interests);

        //모집프로젝트 목록 상태 set
        setRecruitedArticles(res.data.data.articles);

        //좋아요프로젝트 목록 상태 set
        setLikedArticles(res.data.data.heartArticles);
      });
  }, []);

  const tabContArr = [
    { tabTitle: '모집한 프로젝트', tabCont: recruitedArticles },
    { tabTitle: '좋아요한 프로젝트', tabCont: likedArticles },
  ];

  const onClickMenu = (idx) => {
    setActiveMenu(idx);
  };

  const onlinkToEdit = () => {
    location.href = `/mypage/edit/${profileData.memberId}`;
    // location.href = `/mypage/edit/${currentUser.memberId}`;
  };

  const onMemberDelete = () => {
    let isGo = window.confirm('탈퇴하시겠습니까?');

    if (isGo) {
      window.alert('탈퇴되었습니다');
      // axios.delete(`/members/${currentUser.memberId}`); // 서버에 탈퇴 요청
      // 로그인 여부 상태 초기화 코드 자리
      // 로그인된 유저의 정보 (유저 아이디) 상태 초기화 코드 자리
      window.location = '/';
    } else {
      console.log('stay');
    }
  };

  return (
    <Container>
      <div className="profile-card">
        <ProfileCard
          onEditProfile={() => {
            onlinkToEdit();
          }}
          onDeleteProfile={() => {
            onMemberDelete();
          }}
        />
      </div>
      <div className="skill-stack">
        <SkillStackView />
      </div>
      <div className="interest">
        <InterestView />
      </div>
      <div className="article-area">
        <ul className="article-menu">
          {tabContArr.map((menu, idx) => {
            return (
              // 메뉴 버튼
              <div
                role="presentation"
                key={idx}
                className={activeMenu === idx ? 'menu active-menu' : 'menu'}
                onClick={() => onClickMenu(idx)}
              >
                {menu.tabTitle}
                <div className="menu-content-count">{`총 ${menu.tabCont.length}개 프로젝트`}</div>
              </div>
            );
          })}
        </ul>
        <ArticlesGrid>
          {tabContArr[activeMenu].tabCont.map((article, idx) => {
            return (
              <ArticleCard
                key={idx}
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
        </ArticlesGrid>
      </div>
    </Container>
  );
};

export default MyPage;
