import styled from 'styled-components';
import { inputTitleCheckState, inputTitleState } from '../atom/atom';
import { useRecoilValue, useRecoilState } from 'recoil';

const Input = styled.input`
  width: 100%;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--black);
  outline: none;
  border: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;

  font-size: 25px;
  line-height: 0;
  font-weight: 500;

  &:hover,
  &:focus,
  &:active {
    border-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }

  ::placeholder {
    color: lightgray;
    font-weight: 500;
  }
`;

const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;

  .warning {
    color: red;
    font-size: 13px;
  }
`;

// 사용 예시
// const [input, setInput] = useState('');
// <InputTitle placeholder={'제목을 입력하세요'} value={input} onChange={setInput} />

const InputTitle = ({ placeholder, value, onChange }) => {
  const [inputTitleCheck, setInputTitleCheck] =
    useRecoilState(inputTitleCheckState);
  const inputTitle = useRecoilValue(inputTitleState);

  const onblur = () => {
    const blank_pattern = /^\s+|\s+$/g;
    if (inputTitle === '' || inputTitle.replace(blank_pattern, '') == '') {
      setInputTitleCheck(false);
    } else {
      setInputTitleCheck(true);
    }
  };

  return (
    <Container>
      <Input
        placeholder={placeholder}
        value={value}
        onChange={(e) => onChange(e.target.value)}
        onBlur={(e) => onblur(e.target.value)}
      />
      {inputTitleCheck ? (
        ''
      ) : (
        <div className="warning">내용을 입력해주세요.</div>
      )}
    </Container>
  );
};

export default InputTitle;
