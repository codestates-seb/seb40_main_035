import styled from 'styled-components';

const Input = styled.input`
  width: auto;
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--grey-dark);
  outline: none;
  border: 1px solid var(--purple-medium);
  transition: 300ms ease-in-out;
  white-space: nowrap;
  font-size: 16px;
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

const DefaultInput = ({ placeholder, value, onChange }) => {
  return (
    <Input
      placeholder={placeholder}
      value={value}
      onChange={(e) => onChange(e.target.value)}
    />
  );
};

export default DefaultInput;
