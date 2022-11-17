import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { activeIdxState, selectedTagsState } from '../atom/atom';
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
} from 'react-icons/si';
import { RiDeleteBack2Fill } from 'react-icons/ri';

const Container = styled.div`
  color: var(--grey-dark);
  font-size: 15px;
  width: 100%;
  height: auto;
  min-width: 250px;

  .tab-menu {
    border-bottom: 1px solid var(--purple-medium);
    height: 50px; // 수정

    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }

  .tab-title {
    cursor: pointer;

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

  .tab-content {
    display: flex;
    flex-wrap: wrap;
  }

  .skill-tag {
    border: 1px solid var(--purple-medium);
    border-radius: 25px;
    background-color: white;
    font-size: 15px;
    cursor: pointer;

    display: flex;
    align-items: center;
    margin: 15px 15px 15px 0;
    padding: 7px 10px;
  }

  .skill-tag:hover {
    border: 1px solid var(--purple);

    transition: all 0.5s;
  }

  .skill-tag-img {
    width: 20px;
    height: 20px;

    margin-right: 6px;
    border-radius: 100%;
    border: 2px solid white;
    background-color: white;
  }

  .skill-tag-input {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 100%;
    height: 30px;
    margin: 15px 15px;
    padding: 8px 10px;
  }

  .skill-tag-input:focus {
    outline: 4px solid var(--purple-medium);
    border: 1px solid var(--purple);
  }

  .selected-tags {
    width: 100%;
  }

  .selected-tags > ul {
    min-height: 67px;
    display: flex;
    flex-wrap: wrap;
  }

  .skill-selected-tag {
    border: 1px solid var(--purple);
    border-radius: 25px;
    background-color: var(--purple);
    color: white;
    cursor: pointer;

    display: flex;
    align-items: center;
    margin: 15px 15px 15px 0;
    padding: 7px 10px;
  }

  .skill-selected-tag > .delete {
    margin: 2px 0 0 5px;
    display: flex;
    align-items: center;
  }
`;

function SkillStackSelect() {
  const [activeIdx, setActiveIdx] = useRecoilState(activeIdxState);
  const [selectedTags, setSelectedTags] = useRecoilState(selectedTagsState);

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
      tabCont: [],
    },
  ];

  const onTabClick = (index) => {
    setActiveIdx(index); // 클릭한 탭으로 활성화 탭 변경
  };

  const onInputKeyUp = (e) => {
    const filterTarget = selectedTags.filter(
      (el) => el.skillName === e.target.value,
    );
    // 중복 확인 위한 arr(중복X:null, 중복:값O)

    if (
      e.key === 'Enter' &&
      filterTarget.length === 0 &&
      e.target.value.length > 0
    ) {
      let tagObj = {};
      tagObj.skillName = e.target.value;
      setSelectedTags([...selectedTags, tagObj]); // 태그가 추가된 arr를 선택태그리스트 값으로 재설정
      e.target.value = '';
    } else if (
      filterTarget.length !== 0 &&
      e.key === 'Enter' // 중복 태그 시
    ) {
      e.target.value = '';
    }
  };

  const onDeleteClick = (index) => {
    // const newSelectedTags = [...selectedTags];
    const filtered = selectedTags.filter((el, idx) => idx !== index);
    setSelectedTags(filtered);
  };

  const onTagClick = (e) => {
    const filterTarget = selectedTags.filter(
      (el) => el.skillName === e.target.textContent,
    );

    if (filterTarget.length === 0) {
      let tagObj = {};
      tagObj.skillName = e.target.textContent;
      setSelectedTags([...selectedTags, tagObj]);
    } else if (
      filterTarget.length !== 0 // 중복 태그 시
    ) {
      {
        ('');
      }
    }
  };

  return (
    <Container>
      <div className="selected-tags">
        <ul>
          {
            // 선택, 입력된 태그들 표시
            selectedTags.map((tag, idx) => (
              <li key={idx} className="skill-selected-tag">
                <div>{tag.skillName}</div>
                <div
                  className="delete"
                  onClick={() => onDeleteClick(idx)}
                  aria-hidden="true"
                >
                  <RiDeleteBack2Fill />
                </div>
              </li>
            ))
          }
        </ul>
      </div>
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
      <div className="tab-content">
        {
          // 기타 직접 입력
          activeIdx === 2 ? (
            <input
              className="skill-tag-input"
              placeholder="기술 태그를 직접 입력해주세요."
              onKeyUp={onInputKeyUp}
            ></input>
          ) : (
            // FE, BE 선택 입력
            tabContArr[activeIdx].tabCont.map((skillTag, idx) => {
              return (
                <button
                  key={idx}
                  className={
                    selectedTags.filter((el) => el.skillName === skillTag.name)
                      .length !== 0
                      ? 'skill-tag skill-selected-tag'
                      : 'skill-tag'
                  }
                  onClick={onTagClick}
                >
                  {skillTag.img}
                  {skillTag.name}
                </button>
              );
            })
          )
        }
      </div>
    </Container>
  );
}

export default SkillStackSelect;
