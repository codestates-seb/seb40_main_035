import styled from 'styled-components';

const Button = styled.button`
  height: 36px;
  background-color: var(--purple-light);
  padding: 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--purple);
  border: 1px solid var(--purple);
  font-size: 15px;
  font-weight: 500;
  line-height: 18px;
  white-space: nowrap;
  transition: 300ms ease-in-out;

  &:hover {
    background-color: var(--purple-medium);
    color: var(--purple);
  }

  &:active {
    background-color: var(--purple);
    color: var(--purple-light);
  }
`;

const MiniButton = ({ text, onClick }) => {
  return <Button onClick={onClick}>{text}</Button>;
};

export default MiniButton;
