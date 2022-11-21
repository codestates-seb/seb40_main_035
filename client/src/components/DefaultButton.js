import styled from 'styled-components';

const Button = styled.button`
  height: 63px;
  background-color: var(--purple-light);
  padding: 10px 20px;
  border-radius: 8px;
  border: none;
  color: var(--purple);
  border: 1px solid var(--purple);
  font-size: 18px;
  font-weight: 800;
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

const DefaultButton = ({ text, onClick }) => {
  return <Button onClick={onClick}>{text}</Button>;
};

export default DefaultButton;
