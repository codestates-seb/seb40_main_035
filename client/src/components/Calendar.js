import React from 'react';
import { useRecoilState, useSetRecoilState } from 'recoil';
import DatePicker from 'react-datepicker';
import styled from 'styled-components';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale';
import {
  startDateState,
  endDateState,
  startDateCheckState,
  endDateCheckState,
} from '../atom/atom';
import { AiFillCalendar } from 'react-icons/ai';

const Custom = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Wrapper = styled.div`
  color: var(--grey-dark);
  width: 100%;
  height: auto;

  display: flex;
  justify-content: start;
  flex-wrap: wrap;

  .react-datepicker-wrapper {
    border: 1px solid var(--purple-medium);
    border-radius: 8px;
    width: 47.5%;
    max-width: 300px;
    min-width: max-content;
    padding: 10px;
    margin-right: 10px;
    display: flex;
    align-items: center;
    flex-direction: row;
  }

  .react-datepicker__input .react-datepicker__day--keyboard-selected,
  .react-datepicker__day--in-range,
  .react-datepicker__day--in-selecting-range,
  .react-datepicker__day--selected {
    background-color: var(--purple) !important;
    color: white !important;
  }

  .react-datepicker__header {
    background-color: var(--purple-light) !important;
    color: var(--gray-dark);
  }
`;

const Label = styled.label`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  svg {
    width: 25px;
    height: 25px;
    cursor: pointer;
  }
`;

const Calendar = () => {
  const [startDate, setStartDate] = useRecoilState(startDateState);
  const [endDate, setEndDate] = useRecoilState(endDateState);
  const setStartDateCheck = useSetRecoilState(startDateCheckState);
  const setEndDateCheck = useSetRecoilState(endDateCheckState);
  const CustomInput = React.forwardRef((props, ref) => {
    return (
      <Custom>
        <Label type="button" onClick={props.onClick} ref={ref}>
          <div className="text">{props.value || props.placeholder}</div>
          <AiFillCalendar color="775CBB" size="28px" />
        </Label>
      </Custom>
    );
  });
  CustomInput.displayName = 'CustomInput';

  return (
    <Wrapper>
      <DatePicker
        locale={ko}
        selected={startDate}
        onChange={(date) => {
          setStartDate(date);
          setStartDateCheck(true);
        }}
        placeholderText="시작 예정 일"
        dateFormat="yyyy-MM-dd"
        dateFormatCalendar="yyyy년 MM월"
        selectsStart
        startDate={startDate}
        endDate={endDate}
        minDate={new Date()}
        customInput={<CustomInput />}
      />
      <DatePicker
        locale={ko}
        selected={endDate}
        onChange={(date) => {
          setEndDate(date);
          setEndDateCheck(true);
        }}
        placeholderText="마감 예정 일"
        dateFormat="yyyy-MM-dd"
        dateFormatCalendar="yyyy년 MM월"
        selectsEnd
        startDate={startDate}
        endDate={endDate}
        minDate={startDate}
        customInput={<CustomInput />}
      />
    </Wrapper>
  );
};

export default Calendar;
