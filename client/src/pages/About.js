import styled from 'styled-components';
import ProfileTeams from '../components/ProfileTeams';

const AboutContainer = styled.div`
  min-height: calc(100vh - 62px); //전체화면-헤더 높이
  width: 100vw;
  display: flex;
  background-image: linear-gradient(120deg, #cfcfe9 0%, #775cbb 100%);
  justify-content: center;

  margin: 0 -170px;
  min-width: fit-content;
  @media screen and (max-width: 830px) {
    margin: 0 -50px;
  }
  @media screen and (max-width: 500px) {
    margin: 0 -15px;
  }

  .about-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: auto;
  }
  .title-profile {
    margin-top: 150px;
    font-size: 50px;
    color: white;
    font-weight: 800;
  }
  .body-profile {
    margin-top: 30px;
    color: var(--purple-light);
    font-weight: 500;
    margin-bottom: 20px;
    text-align: center;
  }
  .profile-teams-wrapper {
    display: flex;
    margin-bottom: 150px;
  }
`;

const About = () => {
  return (
    <AboutContainer>
      <div className="about-wrapper">
        <div className="title-profile">Meet Our Team</div>
        <div className="body-profile">
          삼삼오오는 &nbsp;
          <strong>
            프로젝트와 스터디를 수월하게 구성하기 위한 커뮤니티 서비스
          </strong>
          로,
          <br />
          개발자들이 프로젝트나 스터디를
          <strong> 쉽고 빠르게 모집할 수 있도록</strong> 삼삼오오를 기획하게
          되었습니다.
        </div>
        <div className="profile-teams-wrapper">
          <ProfileTeams />
        </div>
      </div>
    </AboutContainer>
  );
};

export default About;
