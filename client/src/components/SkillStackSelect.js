import { useRecoilState } from 'recoil';
import styled from 'styled-components';
import { activeIdxState, selectedSkillstacksState } from '../atom/atom';
import { RiDeleteBack2Fill } from 'react-icons/ri';
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

const InterestSelectWrapper = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;

  .selected-tag-list,
  .tag-list {
    display: flex;
    flex-wrap: wrap;
  }

  .selected-tag-list {
    min-height: 52px;
  }

  .tag-list {
    margin-bottom: -10px;
    overflow: hidden;
  }

  .tab-menu {
    border-bottom: 1px solid var(--purple-medium);
    margin-bottom: 10px;

    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }

  .tab-title {
    cursor: pointer;
    font-size: 15px;

    width: 100% auto;
    height: 100%;
    margin-right: 15px;
    padding: 10px 10px;

    background-color: transparent;
    border: none;
  }

  .active-title {
    color: var(--purple);
    border-bottom: 3px solid var(--purple);
  }
`;

const TagList = styled.ul`
  display: flex;
  flex-wrap: wrap;
`;

const TagBtn = styled.button`
  border: 1px solid var(--purple-medium);
  border-radius: 25px;
  background-color: white;
  font-size: 15px;
  white-space: nowrap;
  cursor: pointer;
  transition: all 0.5s;
  display: flex;
  align-items: center;
  margin-right: 10px;
  margin-bottom: 10px;
  padding: 7px 10px;

  &:hover {
    border: 1px solid var(--purple);
  }

  &.selected-tag {
    border: 1px solid var(--purple);
    border-radius: 25px;
    background-color: var(--purple);
    color: white;
  }

  .interest-tag-img {
    width: 20px;
    height: 20px;
    margin-right: 4px;
    border-radius: 100%;
    border: 2px solid white;
    background-color: white;
  }

  .delete-btn {
    margin-left: 5px;
    display: flex;
    align-items: center;
    order: 2;
  }
`;

const tabContArr = [
  {
    tabTitle: '프론트엔드',
    tabCont: [
      {
        name: 'JavaScript',
        img: <SiJavascript className="interest-tag-img" color="#F7DF1E" />,
      },
      {
        name: 'TypeScript',
        img: <SiTypescript className="interest-tag-img" color="#3178C6" />,
      },
      {
        name: 'React',
        img: <SiReact className="interest-tag-img" color="#61DAFB" />,
      },
      {
        name: 'Vue',
        img: <SiVuedotjs className="interest-tag-img" color="#4FC08D" />,
      },
      {
        name: 'Svelte',
        img: <SiSvelte className="interest-tag-img" color="#FF3E00" />,
      },
      {
        name: 'Nextjs',
        img: <SiNextdotjs className="interest-tag-img" color="#000000" />,
      },
    ],
  },
  {
    tabTitle: '백엔드',
    tabCont: [
      {
        name: 'Java',
        img: <SiJava className="interest-tag-img" color="#000000" />,
      },
      {
        name: 'Spring',
        img: <SiSpring className="interest-tag-img" color="#6DB33F" />,
      },
      {
        name: 'Nodejs',
        img: <SiNodedotjs className="interest-tag-img" color="#339933" />,
      },
      {
        name: 'Nextjs',
        img: <SiNextdotjs className="interest-tag-img" color="#000000" />,
      },
      {
        name: 'Go',
        img: <SiGo className="interest-tag-img" color="#00ADD8" />,
      },
      {
        name: 'Kotlin',
        img: <SiKotlin className="interest-tag-img" color="#FE8901" />,
      },
      {
        name: 'Express',
        img: <SiExpress className="interest-tag-img" color="#000000" />,
      },
      {
        name: 'MySQL',
        img: <SiMysql className="interest-tag-img" color="#4479A1" />,
      },
      {
        name: 'MongoDB',
        img: <SiMongodb className="interest-tag-img" color="#47A248" />,
      },
      {
        name: 'Python',
        img: <SiPython className="interest-tag-img" color="#3776AB" />,
      },
      {
        name: 'Django',
        img: <SiDjango className="interest-tag-img" color="#092E20" />,
      },
      {
        name: 'php',
        img: <SiPhp className="interest-tag-img" color="#777BB4" />,
      },
      {
        name: 'GraphQL',
        img: <SiGraphql className="interest-tag-img" color="#E10098" />,
      },
      {
        name: 'Firebase',
        img: <SiFirebase className="interest-tag-img" color="#FFCA28" />,
      },
    ],
  },
  {
    tabTitle: '기타',
    tabCont: [
      {
        name: 'Flutter',
        img: <SiFlutter className="interest-tag-img" color="#02569B" />,
      },
      {
        name: 'Switft',
        img: <SiSwift className="interest-tag-img" color="#F05138" />,
      },
      {
        name: 'ReactNative',
        img: <SiReact className="interest-tag-img" color="#61DAFB" />,
      },
      {
        name: 'Unity',
        img: <SiUnity className="interest-tag-img" color="#000000" />,
      },
      {
        name: 'AWS',
        img: <SiAmazonaws className="interest-tag-img" color="#232F3E" />,
      },
      {
        name: 'Kubernetes',
        img: <SiKubernetes className="interest-tag-img" color="#326CE5" />,
      },
      {
        name: 'Docker',
        img: <SiDocker className="interest-tag-img" color="#2496ED" />,
      },
      {
        name: 'Git',
        img: <SiGit className="interest-tag-img" color="#F05032" />,
      },
      {
        name: 'Figma',
        img: <SiFigma className="interest-tag-img" color="#5B0BB5" />,
      },
      {
        name: 'Zeplin',
        img: <GiZeppelin className="interest-tag-img" color="#FF9900" />,
      },
      {
        name: 'Jest',
        img: <SiJest className="interest-tag-img" color="#C21325" />,
      },
      {
        name: 'C',
        img: <SiCplusplus className="interest-tag-img" color="#00599C" />,
      },
    ],
  },
];

const Tag = ({ tag, onClick, isSelected, onDelete, icon, icon2 }) => {
  return (
    <li>
      <TagBtn
        onClick={() => (isSelected ? onDelete(tag) : onClick(tag, icon))}
        className={isSelected ? 'selected-tag' : ''}
      >
        {icon}
        {tag}
        {icon2}
      </TagBtn>
    </li>
  );
};

const SkillStackSelect = () => {
  const [activeIdx, setActiveIdx] = useRecoilState(activeIdxState);
  const [selectedSkillstacks, setSelectedSkillstacks] = useRecoilState(
    selectedSkillstacksState,
  );
  let selectedSkillstacksNameArr = [];

  const onTabClick = (index) => {
    setActiveIdx(index); // 클릭한 탭으로 활성화 탭 변경
  };

  const onDeleteClick = (selectedTag) => {
    setSelectedSkillstacks([
      ...selectedSkillstacks.filter((tag) => tag.name !== selectedTag),
    ]);
  };

  const onTagClick = (selectedTag, icon) => {
    if (!selectedSkillstacks.includes(selectedTag)) {
      let selected = {
        name: selectedTag,
        img: icon,
      };
      setSelectedSkillstacks([...selectedSkillstacks, selected]);
    }
  };

  const selectedSkillstacksName = (selectedSkillstacks, selectedTag) => {
    selectedSkillstacksNameArr = selectedSkillstacks.map((el) => el.name);
    return selectedSkillstacksNameArr.includes(selectedTag);
  };

  return (
    <InterestSelectWrapper>
      <TagList className="selected-tag-list">
        {selectedSkillstacks.map((item, idx) => (
          <Tag
            icon={item.img}
            icon2={<RiDeleteBack2Fill className="delete-btn" />}
            key={idx}
            tag={item.name}
            onDelete={onDeleteClick}
            isSelected={true}
          ></Tag>
        ))}
      </TagList>
      <ul className="tab-menu">
        {tabContArr.map((section, idx) => {
          return (
            <button
              key={idx}
              className={
                activeIdx === idx ? 'tab-title active-title' : 'tab-title'
              }
              onClick={() => onTabClick(idx)}
            >
              {section.tabTitle}
            </button>
          );
        })}
      </ul>
      <TagList className="tag-list">
        {tabContArr[activeIdx].tabCont.map((item, idx) => (
          <Tag
            icon={item.img}
            key={idx}
            tag={item.name}
            onClick={onTagClick}
            onDelete={onDeleteClick}
            isSelected={selectedSkillstacksName(selectedSkillstacks, item.name)}
          />
        ))}
      </TagList>
    </InterestSelectWrapper>
  );
};

export default SkillStackSelect;
