import styled from 'styled-components';

const Button = styled.button`
  width: 76px;
  height: 36px;
  background-color: var(--purple-light);
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--purple);
  border: 1px solid var(--purple);
  font-size: 15px;
  font-weight: 700;
  &:hover {
    background-color: var(--purple);
    color: var(--purple-light);
  }
  &:focus,
  &:active {
    background-color: var(--purple-medium);
    color: var(--purple);
  }
`;

const Minibutton = () => {
  return <Button>테스트</Button>;
};

export default Minibutton;
