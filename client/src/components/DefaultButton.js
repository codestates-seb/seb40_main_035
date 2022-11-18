import styled from 'styled-components';

const Button = styled.button`
  width: 107px;
  height: 63px;
  background-color: var(--purple-light);
  padding: 8px 8px 10px;
  border-radius: 8px;
  border: none;
  color: var(--purple);
  border: 1px solid var(--purple);
  font-size: 18px;
  font-weight: 800;
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

const DefaultButton = () => {
  return <Button>테스트</Button>;
};

export default DefaultButton;
