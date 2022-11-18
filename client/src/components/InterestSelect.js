/* eslint-disable no-unused-vars */
import { useRecoilState } from 'recoil';
import styled from 'styled-components';
import { selectedInterestsState } from '../atom/atom';
import { RiDeleteBack2Fill } from 'react-icons/ri';

const interestList = [
  '금융',
  '제조',
  '에너지/친환경',
  '유통/물류',
  '미디어',
  '의료/헬스 케어',
  '건설',
  '교육',
  '기타',
];

const InterestSelectWrapper = styled.div`
  width: 100%;
  height: auto;
  display: flex;
  flex-direction: column;
  border: 2px solid lightskyblue;

  .selected-tag-list,
  .tag-list {
    display: flex;
    flex-wrap: wrap;
  }

  .selected-tag-list {
    margin-bottom: 10px;
    border-bottom: 1px solid var(--purple-medium);
  }

  .tag-list {
    margin-bottom: -10px;
    overflow: hidden;
  }
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
  padding: 5px 15px;

  &:hover {
    border: 1px solid var(--purple);
  }

  &.selected-tag {
    border: 1px solid var(--purple);
    border-radius: 25px;
    background-color: var(--purple);
    color: white;
  }

  .delete-btn {
    padding: 5px;
    margin: -5px -5px -5px 0;
    display: flex;
    align-items: center;
  }
`;

const Tag = ({ item, onClick, isSelected, onDelete }) => {
  return (
    <li>
      <TagBtn onClick={onClick} className={isSelected ? 'selected-tag' : ''}>
        {item}
        {onDelete && (
          <div
            className="delete-btn"
            onClick={() => onDelete(item)}
            aria-hidden="true"
          >
            <RiDeleteBack2Fill />
          </div>
        )}
      </TagBtn>
    </li>
  );
};

const InterestSelect = () => {
  const [selectedInterests, setSelectedInterest] = useRecoilState(
    selectedInterestsState,
  );

  const onDeleteClick = (selectedTag) => {
    setSelectedInterest([
      ...selectedInterests.filter((tag) => tag !== selectedTag),
    ]);
  };

  const onTagClick = (e) => {
    const selectedTag = e.target.textContent;
    if (!selectedInterests.includes(selectedTag)) {
      setSelectedInterest([...selectedInterests, selectedTag]);
    }
  };

  return (
    <InterestSelectWrapper>
      <ul className="selected-tag-list">
        {selectedInterests.map((item, idx) => (
          <Tag
            key={idx}
            item={item}
            onDelete={onDeleteClick}
            isSelected={true}
          ></Tag>
        ))}
      </ul>
      <ul className="tag-list">
        {interestList.map((item, idx) => (
          <Tag
            key={idx}
            item={item}
            onClick={onTagClick}
            isSelected={selectedInterests.includes(item)}
          />
        ))}
      </ul>
    </InterestSelectWrapper>
  );
};

export default InterestSelect;
