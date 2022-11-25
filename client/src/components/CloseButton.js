import styled from 'styled-components';
import { GrClose } from 'react-icons/gr';

const Button = styled.button`
  width: 30px;
  height: 30px;
  padding-top: 2px;
  border-radius: 15px;
  border: none;
  background-color: transparent;
  transition: 300ms ease-in-out;

  svg path {
    stroke: var(--purple);
  }

  &:hover {
    background-color: var(--purple-medium);
  }

  &:active {
    background-color: var(--purple);
    svg path {
      stroke: #fff;
    }
  }
`;

const CloseButton = ({ onClick, className }) => {
  return (
    <Button className={className} onClick={onClick}>
      <GrClose />
    </Button>
  );
};

export default CloseButton;
