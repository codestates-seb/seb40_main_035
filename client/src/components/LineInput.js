import styled from 'styled-components';
import Message from './Message';

const Container = styled.div`
  width: 100%;
  margin-bottom: 30px;
`;

const Input = styled.input`
  width: 100%;
  height: 36px;
  background-color: transparent;
  border: none;
  border-bottom: 1px solid;
  border-color: ${(props) => (props.isError ? 'red' : 'var(--purple)')};

  &:focus {
    outline: none;
  }

  &:disabled {
    border-color: grey;
  }
`;

const LineInput = ({
  id,
  value,
  onChange,
  isError,
  onBlur,
  type,
  disabled,
  message,
}) => {
  return (
    <Container className={disabled ? 'disabled' : ''}>
      <div className="input-section">
        <Input
          id={id}
          value={value}
          onChange={(e) => onChange(e.target.value)}
          onBlur={onBlur}
          isError={isError}
          disabled={disabled}
          type={type || 'text'}
        />
      </div>
      <Message disabled={disabled} isError={isError} text={message} />
    </Container>
  );
};

export default LineInput;
