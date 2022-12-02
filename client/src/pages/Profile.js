import styled from 'styled-components';
import ProfileCard from '../components/ProfileCard';
import SkillStackView from '../components/SkillStackView';
import InterestView from '../components/InterestView';
import ArticleCard from '../components/ArticleCard';
import {
  recruitedArticlesState,
  skillStackViewState,
  interestViewState,
  userProfileState,
} from '../atom/atom';
import { useRecoilState, useSetRecoilState } from 'recoil';
import { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import axios from 'axios';
import ArticlesGrid from '../components/ArticlesGrid';
import getSkills from '../utils/getSkills';

const Container = styled.div`
  min-height: calc(100vh - 62px); //전체화면-헤더 높이
  width: fit-content;
  color: var(--black);

  display: flex;
  flex-direction: column;
  align-items: center;

  .profile-card {
    margin-top: 40px;
    width: 100%;
  }

  .skill-stack {
    border-radius: 8px;
    background-color: white;
    width: 100%;
    margin-top: 20px;
    padding: 20px 30px;
  }

  .interest {
    border-radius: 8px;
    background-color: white;
    width: 100%;
    margin-top: 20px;
    padding: 20px 30px 30px 30px;
  }

  .article-area {
    width: calc(100vw - 360px);
    min-width: 100%;
  }

  .article-menu {
    padding: 60px 0 0 0;
    display: flex;
    margin-bottom: 20px;
  }

  .menu {
    background-color: transparent;
    padding: 0 20px 0 0;
    text-align: start;
    font-size: 18px;
    font-weight: 600;
    cursor: pointer;
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
  const setSkillStackView = useSetRecoilState(skillStackViewState);
  const setProfileData = useSetRecoilState(userProfileState);
  const setInterestView = useSetRecoilState(interestViewState);
  const [recruitedArticles, setRecruitedArticles] = useRecoilState(
    recruitedArticlesState,
  );
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

      setSkillStackView(getSkills(res.data.data.skills));

      setInterestView(res.data.data.interests);

      setRecruitedArticles(res.data.data.articles);
    });
  }, []);

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
          <div role="presentation" className="menu">
            모집한 프로젝트
            <div className="menu-content-count">{`총 ${recruitedArticles.length}개 프로젝트`}</div>
          </div>
        </ul>

        <ArticlesGrid>
          {recruitedArticles.map((article, idx) => {
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

export default Profile;
