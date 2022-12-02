import styled from 'styled-components';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import { selectedSkillstacksState } from '../atom/atom';
import SkillStackSelect from './SkillStackSelect';
import ExtendedButton from './ExtendedButton';
import CloseButton from './CloseButton';
import { TbFilter } from 'react-icons/tb';

const FilterSelectorWrapper = styled.div`
  .filter-modal-toggle {
    height: 36px;
    margin-left: 10px;
    padding-left: 4px;

    background-color: var(--purple-light);
    border: solid 2px var(--purple);
    border-radius: 20px;
    padding: 0 0.9em;

    font-weight: 700;
    font-size: 13px;
    color: var(--purple);

    transition: 300ms ease-in-out;

    display: flex;
    align-items: center;
    justify-content: center;

    &.opened {
      background-color: var(--purple-medium);
    }

    svg {
      stroke: var(--purple);
      margin-right: -3px;
    }
  }

  .filter-selector-modal {
    width: 695px;
    max-width: 100%;
    margin-top: 10px;
    position: absolute;
    right: 0;
    z-index: 1;
    background-color: #fff;
    border: 1px solid var(--purple);
    border-radius: 8px;

    .filter-contents {
      margin: 24px;

      p {
        margin-bottom: 22px;
        text-align: left;
        font-weight: 700;
        font-size: 15px;
        color: var(--black);
      }
    }

    .close-btn {
      position: absolute;
      top: 12px;
      right: 12px;
    }
  }
`;

const SkillFilterSelector = ({ setSkillFilter, setModalDisplay, isOpened }) => {
  const [selectedSkillStacks, setSelectedSkillStacks] = useRecoilState(
    selectedSkillstacksState,
  );
  const [isFiltered, setIsFiltered] = useState(false);

  const onToggle = () => {
    setModalDisplay();
    if (!isFiltered) setSelectedSkillStacks([]);
  };

  const onApply = () => {
    setModalDisplay();
    setSkillFilter(
      selectedSkillStacks.map((el) => {
        return el.name;
      }),
    );
    setIsFiltered(true);
  };

  return (
    <FilterSelectorWrapper>
      <button
        className={
          isOpened ? 'filter-modal-toggle opened' : 'filter-modal-toggle'
        }
        onClick={() => onToggle()}
      >
        필터링
        <TbFilter size={23} />
      </button>
      {isOpened && (
        <div className="filter-selector-modal">
          <div className="filter-contents">
            <p>기술 스택으로 필터를 설정해보세요!</p>
            <SkillStackSelect />
          </div>
          <ExtendedButton
            text="적용하기"
            onClick={() => onApply()}
            className="apply-btn"
          />
          <CloseButton className="close-btn" onClick={() => onToggle()} />
        </div>
      )}
    </FilterSelectorWrapper>
  );
};

export default SkillFilterSelector;
