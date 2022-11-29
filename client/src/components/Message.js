import styled from 'styled-components';

const Span = styled.span`
  font-size: 13px;
  color: var(--purple);

  &.error {
    color: red;
  }
`;

const Message = ({ isError, text }) => {
  return <Span className={isError ? 'error' : ''}>{text}</Span>;
};

export default Message;
