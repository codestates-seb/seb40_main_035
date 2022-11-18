/* eslint-disable no-unused-vars */
import { useRecoilState } from 'recoil';
import styled from 'styled-components';
import { selectedInterestsState } from '../atom/atom';
import { RiDeleteBack2Fill } from 'react-icons/ri';
import {
  FcCurrencyExchange,
  FcSupport,
  FcFlashOn,
  FcShipped,
  FcVideoCall,
  FcLike,
  FcOrganization,
  FcGraduationCap,
  FcQuestions,
} from 'react-icons/fc';

const interestContArr = [
  { name: '금융', img: <FcCurrencyExchange className="interest-tag-img" /> },
  { name: '제조', img: <FcSupport className="interest-tag-img" /> },
  { name: '에너지/친환경', img: <FcFlashOn className="interest-tag-img" /> },
  { name: '유통/물류', img: <FcShipped className="interest-tag-img" /> },
  { name: '미디어', img: <FcVideoCall className="interest-tag-img" /> },
  { name: '의료/헬스 케어', img: <FcLike className="interest-tag-img" /> },
  { name: '건설', img: <FcOrganization className="interest-tag-img" /> },
  { name: '교육', img: <FcGraduationCap className="interest-tag-img" /> },
  { name: '기타', img: <FcQuestions className="interest-tag-img" /> },
];

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
    margin-bottom: 10px;
    border-bottom: 1px solid var(--purple-medium);
  }

  .tag-list {
    margin-bottom: -10px;
    overflow: hidden;
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

const Tag = ({ tag, onClick, isSelected, onDelete, icon }) => {
  return (
    <li>
      <TagBtn
        onClick={() => (isSelected ? onDelete(tag) : onClick(tag))}
        className={isSelected ? 'selected-tag' : ''}
      >
        {icon}
        {tag}
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

  const onTagClick = (selectedTag) => {
    if (!selectedInterests.includes(selectedTag)) {
      setSelectedInterest([...selectedInterests, selectedTag]);
    }
  };

  return (
    <InterestSelectWrapper>
      <TagList className="selected-tag-list">
        {selectedInterests.map((item, idx) => (
          <Tag
            icon={<RiDeleteBack2Fill className="delete-btn" />}
            key={idx}
            tag={item}
            onDelete={onDeleteClick}
            isSelected={true}
          ></Tag>
        ))}
      </TagList>
      <TagList className="tag-list">
        {interestContArr.map((item, idx) => (
          <Tag
            icon={item.img}
            key={idx}
            tag={item.name}
            onClick={onTagClick}
            onDelete={onDeleteClick}
            isSelected={selectedInterests.includes(item.name)}
          />
        ))}
      </TagList>
    </InterestSelectWrapper>
  );
};

export default InterestSelect;
