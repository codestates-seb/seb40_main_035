/* eslint-disable no-unused-vars */
import { useCallback, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { BsFillCaretDownFill } from 'react-icons/bs';

const WholeContainer = styled.div`
  width: 200px;
  padding: 10px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-direction: column;

  .title {
    margin-left: 5px;
    margin-bottom: 5px;
    font-weight: 500;
  }
`;

const DropdownContainer = styled.div`
  display: flex;
  justify-content: center;
  &:hover {
    cursor: pointer;
  }

  span {
    font-size: 13px;
  }
`;

const DropdownBody = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 14px;
  border: solid 1px #cfcfe9;
  background-color: var(--purple-light);
  width: 180px;
  border-radius: 20px;
  #down-icon {
    color: var(--purple);
  }
`;

const DropdownMenu = styled.ul`
  justify-content: center;
  align-items: center;
  display: ${(props) => (props.isActive ? `block` : `none`)};
  width: 180px;
  background-color: var(--purple-light);
  position: absolute;
  border: solid 1px var(--purple);
  border-radius: 8px;
`;

const DropdownItem = styled.li`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 14px;
  border-bottom: solid 1px var(--purple);
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

const selectOptions = [
  { value: '학생', id: '학생' },
  { value: '취준생', id: '취준생' },
  { value: '주니어', id: '주니어' },
  { value: '시니어', id: '시니어' },
];
const SelectLevel = () => {
  // 드롭다운 상태 저장 => active ? 펼쳐집니다 : 닫힙니다
  const [isActive, setIsActive] = useState(false);
  // 선택된 데이터 저장
  const [selectedValue, setSelectedValue] = useState(null);
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
    <WholeContainer>
      <span className="title">숙련도</span>
      <DropdownContainer ref={selectInput}>
        <DropdownBody onClick={onActiveToggle}>
          {selectedValue ? (
            <>
              <span>{selectedValue} </span>
              <BsFillCaretDownFill id="down-icon" />
            </>
          ) : (
            <>
              <span>숙련도를 선택해주세요.</span>
              <BsFillCaretDownFill id="down-icon" />
            </>
          )}
        </DropdownBody>
        <DropdownMenu isActive={isActive}>
          {selectOptions.map((item) => (
            <DropdownItem id="item" key={item.id} onClick={onSelect}>
              <span id="item_name">{item.value}</span>
            </DropdownItem>
          ))}
        </DropdownMenu>
      </DropdownContainer>
    </WholeContainer>
  );
};

export default SelectLevel;
