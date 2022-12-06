import styled from 'styled-components';
import jw from '../assets/image/jw.png';
import sb from '../assets/image/sb.png';
import dh from '../assets/image/dh.jpg';
import kw from '../assets/image/kw.jpg';
import ji from '../assets/image/ji.png';
import ch from '../assets/image/ch.png';
import yk from '../assets/image/yk.png';

const ProfileContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  color: white;
  margin-top: 30px;
  padding: 15px;
  text-align: center;

  .team-members-wrapper {
    display: flex;
  }
  .profile-img {
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .team-members {
    padding: 20px;
    color: white;
    font-size: 15px;
  }
  img {
    width: 110px;
  }
  a {
    text-decoration: none;
    &:hover {
      box-shadow: rgba(149, 157, 165, 0.2) 5px 8px 24px;
      transform: translate(0, -5px);
    }
    :visited {
      text-decoration: none;
    }
  }
  .name {
    margin-top: 8px;
    padding: 3px;
  }

  .profile-img-body {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    background-color: var(--purple-medium);
    display: flex;
    justify-content: center;
    align-items: center;
    border: none;
  }
`;

const ProfileTeams = () => {
  const teamBackEnd = [
    {
      name: '서건원',
      part: 'BackEnd',
      imgSrc: kw,
      github: 'https://github.com/GeonwonSeo',
    },
    {
      name: '박영기',
      part: 'BackEnd',
      imgSrc: yk,
      github: 'https://github.com/park-yeong-ki',
    },
    {
      name: '박찬현',
      part: 'BackEnd',
      imgSrc: ch,
      github: 'https://github.com/J4mbo9',
    },
  ];

  const teamFrontEnd = [
    {
      name: '손지원',
      part: 'FrontEnd',
      imgSrc: jw,
      github: 'https://github.com/Sonjiwon0',
    },
    {
      name: '이세비',
      part: 'FrontEnd',
      imgSrc: sb,
      github: 'https://github.com/2seb2',
    },
    {
      name: '최동환',
      part: 'FrontEnd',
      imgSrc: dh,
      github: 'https://github.com/DalDalChoi',
    },
    {
      name: '하정인',
      part: 'FrontEnd',
      imgSrc: ji,
      github: 'https://github.com/JungInHa',
    },
  ];
  return (
    <ProfileContainer>
      <div className="team-members-wrapper">
        {teamBackEnd.map((member, idx) => {
          return (
            <a href={member.github} key={idx} target="_blank" rel="noreferrer">
              <div className="team-members">
                <div className="profile-img">
                  <div className="profile-img-body">
                    <img alt="profile" src={member.imgSrc}></img>
                  </div>
                </div>
                <div className="name">{member.name}</div>
                <div className="part">{member.part}</div>
              </div>
            </a>
          );
        })}
      </div>

      <div className="team-members-wrapper">
        {teamFrontEnd.map((member, idx) => {
          return (
            <a href={member.github} key={idx} target="_blank" rel="noreferrer">
              <div className="team-members" key={idx}>
                <div className="profile-img">
                  <div className="profile-img-body">
                    <img alt="profile" src={member.imgSrc}></img>
                  </div>
                </div>
                <div className="name">{member.name}</div>
                <div className="part">{member.part}</div>
              </div>
            </a>
          );
        })}
      </div>
    </ProfileContainer>
  );
};

export default ProfileTeams;
