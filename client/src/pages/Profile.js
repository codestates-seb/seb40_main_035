import styled from 'styled-components';
import ProfileCard from '../components/ProfileCard';
import SkillStackView from '../components/SkillStackView';
import InterestView from '../components/InterestView';
import ArticleCard from '../components/ArticleCard';
import {
  recruitedArticlesState,
  likedArticlesState,
  skillStackViewState,
  interestViewState,
  userProfileState,
} from '../atom/atom';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';

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
    width: calc(100vw - 340px);
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

const Profile = () => {
  const [activeMenu, setActiveMenu] = useState(0);
  const [skillStackView, setSkillStackView] =
    useRecoilState(skillStackViewState);
  const [setProfileData] = useRecoilState(userProfileState);
  const setInterestView = useSetRecoilState(interestViewState);
  const [recruitedArticles, setRecruitedArticles] = useRecoilState(
    recruitedArticlesState,
  );
  const [likedArticles, setLikedArticles] = useRecoilState(likedArticlesState);
  const { id } = useParams();

  useEffect(() => {
    axios.get(`/members/${id}`).then((res) => {
      const userProfile = {
        memberId: res.data.data.memberId,
        email: res.data.data.email,
        name: res.data.data.name,
        description: res.data.data.description,
        level: res.data.data.level,
        github: res.data.data.github,
      };
      setProfileData(userProfile);

      for (let el of res.data.data.skills) {
        if (el.skillSort === '프론트엔드') {
          setSkillStackView([
            {
              tabTitle: '프론트엔드',
              tabCont: [
                ...skillStackView[0].tabCont,
                {
                  skillId: skillStackView[0].tabCont.length + 1,
                  name: el.name,
                },
              ],
            },
            {
              tabTitle: '백엔드',
              tabCont: [...skillStackView[1].tabCont],
            },
            {
              tabTitle: '기타',
              tabCont: [...skillStackView[2].tabCont],
            },
          ]);
        }
        if (el.skillSort === '백엔드') {
          setSkillStackView([
            {
              tabTitle: '프론트엔드',
              tabCont: [...skillStackView[0].tabCont],
            },
            {
              tabTitle: '백엔드',
              tabCont: [
                ...skillStackView[1].tabCont,
                {
                  skillId: skillStackView[1].tabCont.length + 1,
                  name: el.name,
                },
              ],
            },
            {
              tabTitle: '기타',
              tabCont: [...skillStackView[2].tabCont],
            },
          ]);
        }
        if (el.skillSort !== '백엔드' && el.skillSort !== '프론트엔드') {
          setSkillStackView([
            {
              tabTitle: '프론트엔드',
              tabCont: [...skillStackView[0].tabCont],
            },
            {
              tabTitle: '백엔드',
              tabCont: [...skillStackView[1].tabCont],
            },
            {
              tabTitle: '기타',
              tabCont: [
                ...skillStackView[2].tabCont,
                {
                  skillId: skillStackView[2].tabCont.length + 1,
                  name: el.name,
                },
              ],
            },
          ]);
        }
      }

      setInterestView(res.data.data.interests);

      setRecruitedArticles(res.data.data.articles);

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

  return (
    <Container>
      <div className="profile-card">
        <ProfileCard />
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

        <ul className="article-list">
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
        </ul>
      </div>
    </Container>
  );
};

export default Profile;
