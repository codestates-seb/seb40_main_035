// import { useState } from 'react';
import styled from 'styled-components';
import { useRecoilState } from 'recoil';
import { activeIdxState, selectedTagsState } from '../atom/atom';

// color/보더/커서 -> font -> w/h -> m/p -> flex -> 기타
const Container = styled.div`
  /* border: 1px solid red; */
  /* background-color: lightgray; */
  color: var(--grey-dark);
  font-size: 15px;
  width: 600px;
  height: 200px;

  .tab-menu {
    border-bottom: 1px solid var(--purple-medium);
    height: 50px; // 수정

    display: flex;
    flex-direction: row;
    justify-content: start;
    align-items: center;
  }

  .tab-title {
    /* border: 1px solid green; */
    cursor: pointer;

    width: 100% auto;
    height: 100%;
    margin-right: 15px;
    padding: 10px 10px;
  }

  .active-title {
    color: var(--purple);
    border-bottom: 3px solid var(--purple);
  }

  .tab-content {
    /* border: 1px solid orange; */

    display: flex;
    flex-wrap: wrap;
  }

  .skill-tag {
    border: 1px solid var(--purple-medium);
    border-radius: 25px;
    cursor: pointer;

    margin: 15px 15px 15px 0;
    padding: 7px 10px;
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

  .active-tag {
    background-color: var(--purple);
    color: white;
  }

  .selected-tags > ul {
    height: 65px;
    display: flex;
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
    margin-left: 5px;
  }
`;

function SkillStackSelect() {
  const [activeIdx, setActiveIdx] = useRecoilState(activeIdxState);
  const [selectedTags, setSelectedTags] = useRecoilState(selectedTagsState);

  const tabContArr = [
    {
      tabTitle: '프론트엔드',
      tabCont: ['JavaScript', 'TypeScript', 'React', 'Vue', 'Svelte', 'Nextjs'],
    },
    {
      tabTitle: '백엔드',
      tabCont: [
        'Java',
        'Spring',
        'Nodejs',
        'Nextjs',
        'Go',
        'Kotlin',
        'Express',
        'MySQL',
        'MongoDB',
        'Python',
        'Django',
        'php',
        'GraphQL',
        'Firebase',
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
    const newSelectedTags = [...selectedTags];
    const filterTarget = newSelectedTags.filter(
      (el) => el.tagName === e.target.value,
    );
    // 중복 확인 위한 arr(중복X:null, 중복:값O)

    if (
      e.key === 'Enter' &&
      filterTarget.length === 0 &&
      e.target.value.length > 0 &&
      newSelectedTags.length < 5
    ) {
      let tagObj = {};
      tagObj.tagName = e.target.value;
      newSelectedTags.push(tagObj); // arr에 태그 추가
      setSelectedTags(newSelectedTags); // 태그가 추가된 arr를 선택태그리스트 값으로 재설정
      e.target.value = '';
    } else if (
      (filterTarget.length !== 0 && e.key === 'Enter') || // 중복 태그 시
      (selectedTags.length >= 5 && e.key === 'Enter') // 태그 5개 이상 시
    ) {
      e.target.value = '';
    }
  };

  const onDeleteClick = (index) => {
    const newSelectedTags = [...selectedTags];
    const filtered = newSelectedTags.filter((el, idx) => idx !== index);
    setSelectedTags(filtered);
  };

  const onTagClick = (e) => {
    const newSelectedTags = [...selectedTags];
    console.log(e.target.textContent);

    const filterTarget = newSelectedTags.filter(
      (el) => el.tagName === e.target.textContent,
    );

    if (filterTarget.length === 0 && newSelectedTags.length < 5) {
      let tagObj = {};
      tagObj.tagName = e.target.textContent;
      newSelectedTags.push(tagObj);
      setSelectedTags(newSelectedTags);
    } else if (
      filterTarget.length !== 0 || // 중복 태그 시
      selectedTags.length >= 5 // 태그 5개 이상 시
    ) {
      {
        ('');
      }
    }
    console.log(selectedTags);
  };

  return (
    <Container>
      <div className="selected-tags">
        <ul>
          {
            // 선택, 입력된 태그들 표시
            selectedTags.map((tag, idx) => (
              <li key={idx} className="skill-selected-tag">
                <div>{tag.tagName}</div>
                <div
                  className="delete"
                  onClick={() => onDeleteClick(idx)}
                  aria-hidden="true"
                >
                  x
                </div>
              </li>
            ))
          }
        </ul>
      </div>
      <ul className="tab-menu">
        {tabContArr.map((section, idx) => {
          return (
            <li
              key={idx}
              className={
                activeIdx === idx ? 'tab-title active-title' : 'tab-title'
              }
              onClick={() => onTabClick(idx)}
              aria-hidden="true" // ESlint 오류 해결 - 일단 스크린리더에서 제외
            >
              {section.tabTitle}
            </li>
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
                <div
                  key={idx}
                  className={
                    selectedTags.filter((el) => el.tagName === skillTag)
                      .length !== 0
                      ? 'skill-tag skill-selected-tag'
                      : 'skill-tag'
                  }
                  // className="skill-tag"
                  onClick={onTagClick}
                  aria-hidden="true" // ESlint 오류 해결 - 일단 스크린리더에서 제외
                >
                  {skillTag}
                </div>
              );
            })
          )
        }
      </div>
    </Container>
  );
}

export default SkillStackSelect;
