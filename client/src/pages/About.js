import styled from 'styled-components';
import ProfileTeams from '../components/ProfileTeams';

const AboutContainer = styled.div`
  .about-wrapper {
  }
  .title-profile {
  }
  .body-profile {
  }
  .profile-teams-wrapper {
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
          <ProfileTeams name="서건원" part="BackEnd" />
          <ProfileTeams name="박영기" part="BackEnd" />
          <ProfileTeams name="박찬현" part="BackEnd" />
        </div>
        <div className="profile-teams-wrapper">
          <ProfileTeams name="손지원" part="FrontEnd" />
          <ProfileTeams name="이세비" part="FrontEnd" />
          <ProfileTeams name="최동환" part="FrontEnd" />
          <ProfileTeams name="하정인" part="FrontEnd" />
        </div>
      </div>
    </AboutContainer>
  );
};

export default About;
