import styled from 'styled-components';

const Input = styled.input`
  width: 100%;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--black);
  outline: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;

  font-size: 25px;
  line-height: 0;
  font-weight: 700;

  &:hover,
  &:focus,
  &:active {
    outline-color: var(--purple);
  }

  &:focus,
  &:active {
    box-shadow: 0px 0px 0px 4px var(--purple-medium);
  }
`;

// 사용 예시
// const [input, setInput] = useState('');
// <InputTitle placeholder={'제목을 입력하세요'} value={input} onChange={setInput} />

const InputTitle = ({ placeholder, value, onChange }) => {
  return (
    <Input
      placeholder={placeholder}
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
};

export default InputTitle;
