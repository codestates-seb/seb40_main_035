/* eslint-disable no-unused-vars */
import { useCallback, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { FiChevronDown } from 'react-icons/fi';
import { useRecoilState } from 'recoil';
import { activeDropDownState, selectedLevelState } from '../atom/atom';

const WholeContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;

  .title {
    font-size: 15px;
    margin-left: 6px;
    margin-bottom: 5px;
    font-weight: 500;
  }
`;

const DropdownContainer = styled.div`
  display: flex;
  /* justify-content: center; */

  &:hover {
    cursor: pointer;
  }
  color: var(--black);
  span {
    font-size: 16px;
  }
`;

const DropdownBody = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 14px;
  border: solid 1px
    ${(props) => (props.isActive ? 'var(--purple)' : 'var(--purple-medium)')};
  background-color: var(--purple-light);
  width: 250px;
  border: 1px solid var(--purple-medium);
  border-radius: 8px;

  #down-icon {
    color: ${(props) =>
      props.isActive ? 'var(--purple)' : 'var(--purple-medium)'};
    transform: ${(props) => (props.isActive ? 'rotateX(180deg)' : 'none')};
    transition: 300ms ease-in-out;
  }
  &:hover,
  &:focus,
  &:active {
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

const DropdownMenu = styled.ul`
  justify-content: center;
  align-items: center;
  display: ${(props) => (props.isActive ? `block` : `none`)};

  width: 250px;
  background-color: var(--purple-light);
  position: absolute;
  border: 0.5px solid var(--purple-medium);

  border-radius: 8px;
  color: var(--grey-dark);
`;

const DropdownItem = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;

  padding: 5px 14px;
  border: 0.5px solid var(--purple-medium);
  &:first-child {
    border-top: 1px solid var(--purple-medium);
    border-radius: 8px 8px 0 0;
  }
  &:last-child {
    border-bottom: 1px solid var(--purple-medium);
    border-radius: 0 0 8px 8px;
  }

  &:hover,
  &:focus,
  &:active {
    background-color: var(--purple);
    color: white;
    cursor: pointer;
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

const selectOptions = ['학생', '취준생', '주니어', '시니어'];

const LevelSelect = () => {
  // 드롭다운 상태 저장 => active ? 펼쳐집니다 : 닫힙니다
  const [isActive, setIsActive] = useRecoilState(activeDropDownState);
  // const [isActive, setIsActive] = useState(false);
  // 선택된 데이터 저장
  const [selectedLevel, setSelectedLevel] = useRecoilState(selectedLevelState);
  const selectInput = useRef();

  // 드롭다운 토글 기능
  const onActiveToggle = () => {
    setIsActive((prev) => !prev);
  };
  // 드롭다운 기능

  const onSelect = (e) => {
    setSelectedLevel(e.target.innerText);

    setIsActive((prev) => !prev);
  };

  // 외부 영역 클릭 시 창 닫기
  const handleClickOutSide = (e) => {
    if (isActive && !selectInput.current.contains(e.target)) {
      setIsActive(false);
    }
  };

  useEffect(() => {
    if (isActive) document.addEventListener('mousedown', handleClickOutSide);
    return () => {
      document.removeEventListener('mousedown', handleClickOutSide);
    };
  });

  return (
    <WholeContainer>
      <DropdownContainer ref={selectInput}>
        <DropdownBody onClick={onActiveToggle}>
          {selectedLevel ? (
            <>
              <span>{selectedLevel} </span>

              <FiChevronDown id="down-icon" />
            </>
          ) : (
            <>
              <span>숙련도를 선택해주세요.</span>
              <FiChevronDown id="down-icon" />
            </>
          )}
        </DropdownBody>
        <DropdownMenu isActive={isActive}>
          {selectOptions.map((item) => (
            <DropdownItem id="item" key={item} onClick={onSelect}>
              {item}
            </DropdownItem>
          ))}
        </DropdownMenu>
      </DropdownContainer>
    </WholeContainer>
  );
};

export default LevelSelect;
