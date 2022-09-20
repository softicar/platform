function clamp(value: number, minimum: number, maximum: number) {
	value = Math.max(value, minimum);
	value = Math.min(value, maximum);
	return value;
}
