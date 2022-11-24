/* eslint-disable no-unused-vars */
import styled from 'styled-components';
import { useRecoilValue, useRecoilState } from 'recoil';
import {
  SiJavascript,
  SiTypescript,
  SiReact,
  SiVuedotjs,
  SiSvelte,
  SiNextdotjs,
  SiJava,
  SiSpring,
  SiNodedotjs,
  SiGo,
  SiKotlin,
  SiExpress,
  SiMysql,
  SiMongodb,
  SiPython,
  SiDjango,
  SiPhp,
  SiGraphql,
  SiFirebase,
  SiFlutter,
  SiSwift,
  SiUnity,
  SiAmazonaws,
  SiKubernetes,
  SiDocker,
  SiGit,
  SiFigma,
  SiJest,
  SiCplusplus,
} from 'react-icons/si';
import { GiZeppelin } from 'react-icons/gi';
import { activeMenuState, skillStackViewState } from '../atom/atom';

const SkillViewContainer = styled.div`
  color: var(--grey-dark);
  font-size: 15px;
  width: 100%;
  height: auto;
  min-width: 250px;

  .skill-menu {
    border-bottom: 1px solid var(--purple-medium);
    height: 50px;

    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }
  .menu-title {
    font-size: 15px;
    font-weight: 500;
    cursor: pointer;
    width: 100% auto;
    height: 100%;
    margin-right: 15px;
    padding: 10px 10px;
    background-color: transparent;
    border: none;
  }
  .active-menu {
    color: var(--purple);
    border-bottom: 3px solid var(--purple);
  }
  .skill-tag-list {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: wrap;
  }
  .skill-view-tag {
    border: 1px solid var(--purple-medium);
    border-radius: 25px;
    background-color: white;
    font-size: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 15px 10px 10px 0;
    padding: 7px 10px;
  }
  .selected-skill-view-tag {
    padding: 7px 10px;
    border: 1px solid var(--purple);
    border-radius: 25px;
    background-color: var(--purple);
    color: white;
  }

  .skill-tag-img {
    width: 20px;
    height: 20px;
    margin-right: 6px;
    border-radius: 100%;
    border: 2px solid white;
    background-color: white;
  }
`;
const SkillStackView = () => {
  const [activeMenu, setActiveMenu] = useRecoilState(activeMenuState);
  const skillStackView = useRecoilValue(skillStackViewState);

  const tabContArr = [
    {
      tabTitle: '프론트엔드',
      tabCont: [
        {
          name: 'JavaScript',
          img: <SiJavascript className="skill-tag-img" color="#F7DF1E" />,
        },
        {
          name: 'TypeScript',
          img: <SiTypescript className="skill-tag-img" color="#3178C6" />,
        },
        {
          name: 'React',
          img: <SiReact className="skill-tag-img" color="#61DAFB" />,
        },
        {
          name: 'Vue',
          img: <SiVuedotjs className="skill-tag-img" color="#4FC08D" />,
        },
        {
          name: 'Svelte',
          img: <SiSvelte className="skill-tag-img" color="#FF3E00" />,
        },
        {
          name: 'Nextjs',
          img: <SiNextdotjs className="skill-tag-img" color="#000000" />,
        },
      ],
    },
    {
      tabTitle: '백엔드',
      tabCont: [
        {
          name: 'Java',
          img: <SiJava className="skill-tag-img" color="#000000" />,
        },
        {
          name: 'Spring',
          img: <SiSpring className="skill-tag-img" color="#6DB33F" />,
        },
        {
          name: 'Nodejs',
          img: <SiNodedotjs className="skill-tag-img" color="#339933" />,
        },
        {
          name: 'Nextjs',
          img: <SiNextdotjs className="skill-tag-img" color="#000000" />,
        },
        { name: 'Go', img: <SiGo className="skill-tag-img" color="#00ADD8" /> },
        {
          name: 'Kotlin',
          img: <SiKotlin className="skill-tag-img" color="#FE8901" />,
        },
        {
          name: 'Express',
          img: <SiExpress className="skill-tag-img" color="#000000" />,
        },
        {
          name: 'MySQL',
          img: <SiMysql className="skill-tag-img" color="#4479A1" />,
        },
        {
          name: 'MongoDB',
          img: <SiMongodb className="skill-tag-img" color="#47A248" />,
        },
        {
          name: 'Python',
          img: <SiPython className="skill-tag-img" color="#3776AB" />,
        },
        {
          name: 'Django',
          img: <SiDjango className="skill-tag-img" color="#092E20" />,
        },
        {
          name: 'php',
          img: <SiPhp className="skill-tag-img" color="#777BB4" />,
        },
        {
          name: 'GraphQL',
          img: <SiGraphql className="skill-tag-img" color="#E10098" />,
        },
        {
          name: 'Firebase',
          img: <SiFirebase className="skill-tag-img" color="#FFCA28" />,
        },
      ],
    },
    {
      tabTitle: '기타',
      tabCont: [
        {
          name: 'Flutter',
          img: <SiFlutter className="skill-tag-img" color="#02569B" />,
        },
        {
          name: 'Switft',
          img: <SiSwift className="skill-tag-img" color="#F05138" />,
        },
        {
          name: 'ReactNative',
          img: <SiReact className="skill-tag-img" color="#61DAFB" />,
        },
        {
          name: 'Unity',
          img: <SiUnity className="skill-tag-img" color="#000000" />,
        },
        {
          name: 'AWS',
          img: <SiAmazonaws className="skill-tag-img" color="#232F3E" />,
        },
        {
          name: 'Kubernetes',
          img: <SiKubernetes className="skill-tag-img" color="#326CE5" />,
        },
        {
          name: 'Docker',
          img: <SiDocker className="skill-tag-img" color="#2496ED" />,
        },
        {
          name: 'Git',
          img: <SiGit className="skill-tag-img" color="#F05032" />,
        },
        {
          name: 'Figma',
          img: <SiFigma className="skill-tag-img" color="#5B0BB5" />,
        },
        {
          name: 'Zeplin',
          img: <GiZeppelin className="skill-tag-img" color="#FF9900" />,
        },
        {
          name: 'Jest',
          img: <SiJest className="skill-tag-img" color="#C21325" />,
        },
        {
          name: 'C',
          img: <SiCplusplus className="skill-tag-img" color="#00599C" />,
        },
      ],
    },
  ];
  // 클릭된 탭으로 변경
  const onClickMenu = (idx) => {
    setActiveMenu(idx);
  };

  return (
    <SkillViewContainer>
      {/* 기술 스택 메뉴 */}
      <ul className="skill-menu">
        {tabContArr.map((menu, idx) => {
          return (
            // 메뉴 버튼
            <button
              key={idx}
              className={
                activeMenu === idx ? 'menu-title active-menu' : 'menu-title'
              }
              onClick={() => onClickMenu(idx)}
            >
              {menu.tabTitle}
            </button>
          );
        })}
      </ul>
      {/* 기술스택 보기란 */}
      <ul className="skill-tag-list">
        {tabContArr[activeMenu].tabCont.map((tag, idx) => {
          return (
            <div
              key={idx}
              className={
                skillStackView[activeMenu].tabCont.filter(
                  (selectedTag) => tag.name === selectedTag.name,
                ).length !== 0
                  ? 'skill-view-tag selected-skill-view-tag'
                  : 'skill-view-tag'
              }
            >
              {tag.img}
              {tag.name}
            </div>
          );
        })}
      </ul>
    </SkillViewContainer>
  );
};

export default SkillStackView;
