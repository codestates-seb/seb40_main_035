/* eslint-disable no-unused-vars */
import { useCallback, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { FiChevronDown } from 'react-icons/fi';
import { useRecoilState } from 'recoil';
import { selectedValueState } from '../atom/atom';

const DropdownContainer = styled.div`
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
  border-radius: 8px;
  z-index: 1;

  #down-icon {
    color: ${(props) =>
      props.isActive ? 'var(--purple)' : 'var(--purple-medium)'};
    transform: ${(props) => (props.isActive ? 'rotateX(180deg)' : 'none')};
    transition: 300ms ease-in-out;
  }
`;

const DropdownMenu = styled.ul`
  justify-content: center;
  align-items: center;
  display: ${(props) => (props.isActive ? `block` : `none`)};
  background-color: var(--purple-light);
  border: solid 1px var(--purple-medium);
  border-top: none;
  border-radius: 8px;
  color: var(--grey-dark);
`;

const DropdownItem = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 14px;
  border-bottom: solid 1px var(--purple-medium);
  border-top: none;

  &:hover {
    background-color: var(--purple);
    color: white;
    cursor: pointer;
  }
  &:first-child {
    border-radius: 8px 8px 0 0;
  }
  &:last-child {
    border-bottom: none;
    border-radius: 0 0 8px 8px;
  }
`;

const selectOptions = ['학생', '취준생', '주니어', '시니어'];

const LevelSelect = () => {
  // 드롭다운 상태 저장 => active ? 펼쳐집니다 : 닫힙니다
  const [isActive, setIsActive] = useState(false);
  // 선택된 데이터 저장
  const [selectedValue, setSelectedValue] = useRecoilState(selectedValueState);
  const selectInput = useRef();

  // 드롭다운 토글 기능
  const onActiveToggle = useCallback(() => {
    setIsActive((prev) => !prev);
  }, []);
  // 드롭다운 기능
  const onSelect = useCallback((e) => {
    const targetId = e.target.id;

    if (targetId === 'item_name') {
      setSelectedValue(e.target.parentElement.innerText);
    } else if (targetId === 'item') {
      setSelectedValue(e.target.innerText);
    }
    setIsActive((prev) => !prev);
  }, []);

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

  const onCloseBox = () => {
    setIsActive(false);
    selectInput.current.focus();
    setSelectedValue(null);
  };

  return (
    <>
      <DropdownContainer ref={selectInput}>
        <DropdownBody onClick={onActiveToggle} isActive={isActive}>
          {selectedValue ? (
            <>
              <span>{selectedValue}</span>
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
            <DropdownItem id="item" key={item.toString()} onClick={onSelect}>
              <span id="item_name">{item}</span>
            </DropdownItem>
          ))}
        </DropdownMenu>
      </DropdownContainer>
    </>
  );
};

export default LevelSelect;
