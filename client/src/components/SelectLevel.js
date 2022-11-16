/* eslint-disable no-unused-vars */
import { useCallback, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import { BsFillCaretDownFill } from 'react-icons/bs';

const DropdownContainer = styled.div`
  display: flex;
  justify-content: center;
  &:hover {
    cursor: pointer;
  }
`;

const DropdownBody = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 6px 14px;
  border: solid 1px var(--purple);
  background-color: var(--purple-light);
  width: 210px;
  border-radius: 20px;
  #down-icon {
    color: var(--purple);
  }
`;

const DropdownMenu = styled.ul`
  justify-content: center;
  align-items: center;
  display: ${(props) => (props.isActive ? `block` : `none`)};
  width: 210px;
  background-color: var(--purple-light);
  position: absolute;
  border: solid 1px var(--purple);
  border-radius: 8px;
  font-size: 15px;
`;

const DropdownItemContainer = styled.li`
  display: flex;
  font-size: 15px;
  font-weight: 500;
  justify-content: space-between;
  align-items: center;
  padding: 9px 14px;
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

  return (
    <DropdownContainer>
      <DropdownBody>
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
          <DropdownItemContainer id="item" key={item.id}>
            <span id="item_name">{item.value}</span>
          </DropdownItemContainer>
        ))}
      </DropdownMenu>
    </DropdownContainer>
  );
};

export default SelectLevel;
