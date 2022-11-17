import React from 'react';
import { useRecoilState } from 'recoil';
import DatePicker from 'react-datepicker';
import styled from 'styled-components';
import 'react-datepicker/dist/react-datepicker.css';
import { ko } from 'date-fns/esm/locale';
import { startDateState, endDateState } from '../atom/atom';
import { AiFillCalendar } from 'react-icons/ai';

const Custom = styled.div`
  width: 273px;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--grey-dark);
  outline: none;
  border: 1px solid var(--purple-medium);
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
`;

const Wrapper = styled.div`
  display: flex;
  flex-wrap: wrap;

  .react-datepicker-wrapper {
    width: auto;
    flex-direction: row;
  }

  .react-datepicker__day--keyboard-selected,
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

const Title = styled.span`
  color: var(--grey-dark);
  font-size: 18px;
  font-weight: 700;
  width: 100%;
`;

const Label = styled.label`
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

const Calendar = () => {
  const [startDate, setStartDate] = useRecoilState(startDateState);
  const [endDate, setEndDate] = useRecoilState(endDateState);
  const CustomInput = React.forwardRef((props, ref) => {
    return (
      <Custom>
        <Label type="button" onClick={props.onClick} ref={ref}>
          {props.value || props.placeholder}
          <AiFillCalendar color="775CBB" size="28px" />
        </Label>
      </Custom>
    );
  });
  CustomInput.displayName = 'CustomInput';

  return (
    <Wrapper>
      <Title className="title">프로젝트 예정 기간은 언제인가요?</Title>
      <DatePicker
        locale={ko}
        selected={startDate}
        onChange={(date) => setStartDate(date)}
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
        onChange={(date) => setEndDate(date)}
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
